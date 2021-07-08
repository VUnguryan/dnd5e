package com.dnd5e.wiki.controller.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
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
	public DataTablesOutput<RaceDto> getData(@Valid DataTablesInput input, @RequestParam Map<String, String> queryParameters) {
		Setting settings = (Setting) session.getAttribute(SettingRestController.SETTINGS);
		Set<TypeBook> sources = SourceUtil.getSources(settings);
		Specification<Race> specification = bySources(sources);
		specification = addSpecification(specification, (root, query, cb) -> cb.equal(root.get("view"), false));

		input.parseSearchPanesFromQueryParams(queryParameters, Arrays.asList("abilityBonuses", "size", "book"));

		List<AbilityType> filterAbilities = input.getSearchPanes().getOrDefault("abilityBonuses", Collections.emptySet())
			.stream()
			.map(AbilityType::valueOf)
			.collect(Collectors.toList());
		if (!filterAbilities.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> {
				Join<AbilityBonus, Race> bonus = root.join("bonuses", JoinType.LEFT);
				query.distinct(true);
				return cb.and(bonus.get("ability").in(filterAbilities));
			});
		}
		input.getSearchPanes().remove("abilityBonuses");
		List<CreatureSize> raceSizes = input.getSearchPanes().getOrDefault("size", Collections.emptySet())
				.stream()
				.map(CreatureSize::valueOf)
				.collect(Collectors.toList());
		if (!raceSizes.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("size").in(raceSizes));
		}
		List<Book> filterBooks = input.getSearchPanes().getOrDefault("book", Collections.emptySet())
				.stream()
				.map(Book::new)
				.collect(Collectors.toList());
		if (!filterBooks.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("book").in(filterBooks));
		}
		input.getSearchPanes().remove("abilityBonuses");
		input.getSearchPanes().remove("size");
		input.getSearchPanes().remove("book");
		Map<String, List<Item>> options = new HashMap<>();
		repo.countTotalAbilities(settings == null ? EnumSet.of(TypeBook.OFFICAL) : settings.getTypeBooks()).stream()
			.map(c -> new Item(c.getField().getCyrilicName(), String.valueOf(c.getField().name()), c.getTotal(), c.getTotal()))
			.forEach(v -> addItem("abilityBonuses", options, v));
		repo.countTotalSizes(settings == null ? EnumSet.of(TypeBook.OFFICAL) : settings.getTypeBooks())
			.stream()
				.map(c -> new Item(c.getField().getCyrilicName(), c.getField().name(), c.getTotal(), c.getTotal()))
				.forEach(v -> addItem("size", options, v));

		repo.countTotalSpellByBook(settings == null ? EnumSet.of(TypeBook.OFFICAL) : settings.getTypeBooks()).stream()
			.map(c -> new Item(c.getField().getSource(), c.getField().getSource(), c.getTotal(), c.getTotal()))
			.forEach(v -> addItem("book", options, v));

		DataTablesOutput<RaceDto> output =	repo.findAll(input, null, specification, RaceDto::new);
		SearchPanes sPanes = new SearchPanes(options);
		output.setSearchPanes(sPanes);
		return output;
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