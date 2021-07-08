package com.dnd5e.wiki.controller.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.mapping.SearchPanes;
import org.springframework.data.jpa.datatables.mapping.SearchPanes.Item;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dnd5e.wiki.dto.BackgroundDto;
import com.dnd5e.wiki.dto.user.Setting;
import com.dnd5e.wiki.model.AbilityType;
import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.creature.SkillType;
import com.dnd5e.wiki.model.hero.Background;
import com.dnd5e.wiki.repository.BackgroundRepository;
import com.dnd5e.wiki.util.SourceUtil;

@RestController
public class BackgroundRestController {
	@Autowired
	private HttpSession session;

	@Autowired
	private BackgroundRepository repo;

	@GetMapping("/data/backgrounds")
	public DataTablesOutput<BackgroundDto> getData(@Valid DataTablesInput input, @RequestParam Map<String, String> queryParameters) {
		Setting settings = (Setting) session.getAttribute(SettingRestController.SETTINGS);
		Set<TypeBook> sources = SourceUtil.getSources(settings);
		Specification<Background> specification = bySources(sources);
		input.parseSearchPanesFromQueryParams(queryParameters, Arrays.asList("skills", "book"));

		List<SkillType> filterSkills = input.getSearchPanes().getOrDefault("skills", Collections.emptySet())
			.stream()
			.map(SkillType::valueOf)
			.collect(Collectors.toList());
		if (!filterSkills.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> {
				Join<AbilityType, Background> abilityType = root.join("skills", JoinType.LEFT);
				return cb.and(abilityType.in(filterSkills));
			});
		}
		List<Book> filterBooks = input.getSearchPanes().getOrDefault("book", Collections.emptySet())
				.stream()
				.map(Book::new)
				.collect(Collectors.toList());
		if (!filterBooks.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("book").in(filterBooks));
		}
		input.getSearchPanes().remove("skills");
		input.getSearchPanes().remove("book");
		DataTablesOutput<BackgroundDto> output = repo.findAll(input, specification, specification, i -> new BackgroundDto(i));
		Map<String, List<Item>> options = new HashMap<>();

		repo.countTotalSkill().stream()
			.map(c -> new Item(c.getField().getCyrilicName(), c.getField().name(), c.getTotal(), c.getTotal()))
			.forEach(v -> addItem("skills", options, v));
		repo.countTotalBook().stream().
			map(c -> new Item(c.getField().getSource(), c.getField().getSource(), c.getTotal(), c.getTotal()))
			.forEach(v -> addItem("book", options, v));

		SearchPanes sPanes = new SearchPanes(options);
		output.setSearchPanes(sPanes);
		return output;
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