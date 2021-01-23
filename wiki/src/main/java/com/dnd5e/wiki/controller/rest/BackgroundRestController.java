package com.dnd5e.wiki.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dnd5e.wiki.controller.rest.paging.Item;
import com.dnd5e.wiki.controller.rest.paging.SearchPanes;
import com.dnd5e.wiki.controller.rest.paging.SearchPanesOutput;
import com.dnd5e.wiki.dto.BackgroundDto;
import com.dnd5e.wiki.dto.user.Setting;
import com.dnd5e.wiki.model.AbilityType;
import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.creature.SkillType;
import com.dnd5e.wiki.model.hero.Background;
import com.dnd5e.wiki.model.hero.Trait;
import com.dnd5e.wiki.repository.BackgroundRepository;
import com.dnd5e.wiki.util.SourceUtil;

@RestController
public class BackgroundRestController {
	@Autowired
	private HttpSession session;

	@Autowired
	private BackgroundRepository repo;

	@GetMapping("/backgrounds")
	public SearchPanesOutput<BackgroundDto> getData(@Valid DataTablesInput input,
			@RequestParam Map<String, String> searchPanes) {
		List<SkillType> filterSkills = new ArrayList<>();
		for (int j = 0; j <= SkillType.values().length; j++) {
			String abylity = searchPanes.get("searchPanes.skills." + j);
			if (abylity != null) {
				filterSkills.add(SkillType.parse(abylity));
			}
		}
		List<Book> filterBooks = new ArrayList<>();
		for (int j = 0; j <= 21; j++) {
			String type = searchPanes.get("searchPanes.book." + j);
			if (type != null) {
				Book book = new Book();
				book.setSource(type);
				filterBooks.add(book);
			}
		}
		Setting settings = (Setting) session.getAttribute(SettingRestController.SETTINGS);
		Set<TypeBook> sources = SourceUtil.getSources(settings);
		Specification<Background> specification = bySources(sources);
		if (!filterSkills.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> {
				Join<AbilityType, Background> abilityType = root.join("skills", JoinType.LEFT);
				return cb.and(abilityType.in(filterSkills));
			});
		}
		if (!filterBooks.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("book").in(filterBooks));
		}

		DataTablesOutput<BackgroundDto> output = repo.findAll(input, specification, specification, i -> new BackgroundDto(i));
		SearchPanes sPanes = new SearchPanes();
		Map<String, List<Item>> options = new HashMap<>();

		repo.countTotalSkill().stream()
			.map(c -> new Item<String>(c.getField().getCyrilicName(), c.getTotal(), String.valueOf(c.getField()), c.getTotal()))
			.forEach(v -> addItem("skills", options, v));
		repo.countTotalBook().stream().
			map(c -> new Item<String>(c.getField().getSource(), c.getTotal(), String.valueOf(c.getField()), c.getTotal()))
			.forEach(v -> addItem("book", options, v));

		sPanes.setOptions(options);
		SearchPanesOutput<BackgroundDto> spOutput = new SearchPanesOutput<>(output);
		spOutput.setSearchPanes(sPanes);
		return spOutput;
	}

	private Specification<Background> bySources(Set<TypeBook> sources) {
		return (root, query, cb) -> root.get("book").get("type").in(sources);
	}
	
	private Specification<Background> addSpecification(Specification<Background> specification , Specification<Background> addSpecification){
		if (specification == null) {
			return Specification.where(addSpecification);
		}
		return specification.and(addSpecification);
	}
	
	private void addItem(String key, Map<String, List<Item>> options, Item v) {
		options.computeIfAbsent(key, s -> new ArrayList<>()).add(v);
	}
}