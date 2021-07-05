package com.dnd5e.wiki.controller.rest;

import java.util.ArrayList;
import java.util.EnumSet;
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
import com.dnd5e.wiki.dto.RaceDto;
import com.dnd5e.wiki.dto.user.Setting;
import com.dnd5e.wiki.model.AbilityType;
import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.creature.CreatureSize;
import com.dnd5e.wiki.model.hero.AbilityBonus;
import com.dnd5e.wiki.model.hero.race.Race;
import com.dnd5e.wiki.repository.datatable.RaceDatatableRepository;
import com.dnd5e.wiki.util.SourceUtil;

@RestController
public class RaceRestController {
	@Autowired
	private HttpSession session;

	@Autowired
	private RaceDatatableRepository repo;

	@GetMapping("/data/races")
	public DataTablesOutput<RaceDto> getData(@Valid DataTablesInput input, @RequestParam Map<String, String> searchPanes) {
		Setting settings = (Setting) session.getAttribute(SettingRestController.SETTINGS);
		Set<TypeBook> sources = SourceUtil.getSources(settings);
		Specification<Race> specification = bySources(sources);
		specification = addSpecification(specification, (root, query, cb) -> cb.equal(root.get("view"), false));
		List<AbilityType> filterAbilities = new ArrayList<>();
		for (int j = 0; j <= AbilityType.values().length; j++) {
			String name = searchPanes.get("searchPanes.abilityBonuses." + j);
			if (name != null) {
				filterAbilities.add(AbilityType.parse(name));
			}
		}
		if (!filterAbilities.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> {
				Join<AbilityBonus, Race> bonus = root.join("bonuses", JoinType.LEFT);
				query.distinct(true);
				return cb.and(bonus.get("ability").in(filterAbilities));
			});
		}
		List<CreatureSize> raceSizes = new ArrayList<>();
		for (int j = 0; j <= CreatureSize.values().length; j++) {
			String size = searchPanes.get("searchPanes.size." + j);
			if (size != null) {
				raceSizes.add(CreatureSize.parse(size));
			}
		}
		if (!raceSizes.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("size").in(raceSizes));
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
		if (!filterBooks.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("book").in(filterBooks));
		}
		SearchPanes sPanes = new SearchPanes();
		Map<String, List<Item>> options = new HashMap<>();

		repo.countTotalAbilities(settings == null ? EnumSet.of(TypeBook.OFFICAL) : settings.getTypeBooks()).stream()
			.map(c -> new Item(c.getField().getCyrilicName(), c.getTotal(), String.valueOf(c.getField().getCyrilicName()), c.getTotal()))
			.forEach(v -> addItem("abilityBonuses", options, v));
		repo.countTotalSizes(settings == null ? EnumSet.of(TypeBook.OFFICAL) : settings.getTypeBooks())
			.stream()
				.map(c -> new Item(c.getField().getCyrilicName(), c.getTotal(), c.getField().getCyrilicName(), c.getTotal()))
				.forEach(v -> addItem("size", options, v));

		repo.countTotalSpellByBook(settings == null ? EnumSet.of(TypeBook.OFFICAL) : settings.getTypeBooks()).stream()
			.map(c -> new Item(c.getField().getSource(), c.getTotal(), String.valueOf(c.getField().getSource()), c.getTotal()))
			.forEach(v -> addItem("book", options, v));
		DataTablesOutput<RaceDto> output =	repo.findAll(input, specification, specification, RaceDto::new);
		sPanes.setOptions(options); 
		SearchPanesOutput<RaceDto> spOutput = new SearchPanesOutput<>(output);
		spOutput.setSearchPanes(sPanes);
		return spOutput;
	}

	private <T> Specification<T> addSpecification(Specification<T> specification , Specification<T> addSpecification){
		if (specification == null) {
			return Specification.where(addSpecification);
		}
		return specification.and(addSpecification);
	}
	
	private void addItem(String key, Map<String, List<Item>> options, Item v) {
		options.computeIfAbsent(key, s -> new ArrayList<>()).add(v);
	}
	
	private <T> Specification<T> bySources(Set<TypeBook> types) {
		return (root, query, cb) -> root.get("book").get("type").in(types);
	}
}