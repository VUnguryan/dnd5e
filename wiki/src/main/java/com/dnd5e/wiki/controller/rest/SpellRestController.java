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

import com.dnd5e.wiki.dto.ArchitypeDto;
import com.dnd5e.wiki.dto.SpellDto;
import com.dnd5e.wiki.dto.user.Setting;
import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.creature.DamageType;
import com.dnd5e.wiki.model.hero.classes.HeroClass;
import com.dnd5e.wiki.model.spell.MagicSchool;
import com.dnd5e.wiki.model.spell.Spell;
import com.dnd5e.wiki.repository.ArchetypeSpellRepository;
import com.dnd5e.wiki.repository.datatable.SpellDatatableRepository;
import com.dnd5e.wiki.util.SourceUtil;

@RestController
public class SpellRestController {
	@Autowired
	private HttpSession session;

	@Autowired
	private SpellDatatableRepository repo;

	@Autowired
	private ArchetypeSpellRepository aSpellRepo;

	@GetMapping("/data/spells")
	public DataTablesOutput<SpellDto> getData(@Valid DataTablesInput input, @RequestParam Map<String, String> queryParameters) {
		Setting settings = (Setting) session.getAttribute(SettingRestController.SETTINGS);
		Set<TypeBook> sources = SourceUtil.getSources(settings);
		Specification<Spell> specification = bySources(sources);
		input.parseSearchPanesFromQueryParams(queryParameters, Arrays.asList("level", "school", "heroClass", "damageType", "concentration", "ritual", "book"));

		List<Integer> filterLevels = input.getSearchPanes().getOrDefault("level", Collections.emptySet())
			.stream()
			.map(Integer::valueOf)
			.collect(Collectors.toList());
		if (!filterLevels.isEmpty()) {
			specification = addSpecification(specification,  (root, query, cb) -> root.get("level").in(filterLevels));
		}
		List<MagicSchool> filterSchool  = input.getSearchPanes().getOrDefault("school", Collections.emptySet())
				.stream()
				.map(MagicSchool::valueOf)
				.collect(Collectors.toList());
		if (!filterSchool.isEmpty()) {
			specification = addSpecification(specification,(root, query, cb) -> root.get("school").in(filterSchool));
		}
		List<Integer> filterClasses = input.getSearchPanes().getOrDefault("heroClass", Collections.emptySet())
				.stream()
				.map(Integer::valueOf)
				.collect(Collectors.toList());
		if (!filterClasses.isEmpty())
		{
			specification = addSpecification(specification, (root, query, cb) -> {
				Join<HeroClass, Spell> hero = root.join("heroClass", JoinType.LEFT);
				query.distinct(true);
				return cb.and(hero.get("id").in(filterClasses));
			});
		}
		List<DamageType> filterDamageTypes = input.getSearchPanes().getOrDefault("damageType", Collections.emptySet())
				.stream()
				.map(DamageType::valueOf)
				.collect(Collectors.toList());
		if (!filterDamageTypes.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> {
				Join<DamageType, Spell> damageType = root.join("damageType", JoinType.LEFT);
				query.distinct(true);
				return damageType.in(filterDamageTypes);
			});
		}
		Set<String> concentrations = input.getSearchPanes().getOrDefault("concentration",  Collections.emptySet());
		if (concentrations.contains("true")) {
			specification = addSpecification(specification, (root, query, cb) -> cb.equal(root.get("concentration"), true));
		}
		if (concentrations.contains("false")) {
			specification = addSpecification(specification, (root, query, cb) -> cb.equal(root.get("concentration"), false));
		}
		Set<String> rituals = input.getSearchPanes().getOrDefault("ritual",  Collections.emptySet());
		if (rituals.contains("true")) {
			specification = addSpecification(specification, (root, query, cb) -> cb.equal(root.get("ritual"), true));
		} 
		if (rituals.contains("false")) {
			specification = addSpecification(specification, (root, query, cb) -> cb.equal(root.get("ritual"), false));
		}
		Set<String> filterBooks = input.getSearchPanes().getOrDefault("book", Collections.emptySet());
		if (!filterBooks.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("book").get("source").in(filterBooks));
		}
		input.getSearchPanes().clear();
		DataTablesOutput<SpellDto> output = repo.findAll(input, null, specification, SpellDto::new);
		output.getData().forEach(s -> s.setSubClass(aSpellRepo.findAllBySpellId(s.getId(), SourceUtil.getSources(settings)).stream()
				.map(ArchitypeDto::new)
				.collect(Collectors.toList())));
		Map<String, List<Item>> options = new HashMap<>();

		repo.countTotalSpellByLevel().stream()
			.map(c -> new Item(String.valueOf(c.getField()), String.valueOf(c.getField()), c.getTotal(), c.getTotal()))
			.forEach(v -> addItem("level", options, v));

		repo.countTotalSpellBySchool().stream()
			.map(c -> new Item(c.getField().getName(), c.getField().name(), c.getTotal(), c.getTotal()))
			.forEach(v -> addItem("school", options, v));
		
		repo.countTotalSpellByHeroClass().stream()
			.map(c -> new Item(c.getField().getName(), String.valueOf(c.getField().getId()), c.getTotal(), c.getTotal()))
			.forEach(v -> addItem("heroClass", options, v));
		
		repo.countTotalSpellByTypeDamage().stream()
			.map(c -> new Item(c.getField().getCyrilicName(), c.getField().name(), c.getTotal(), c.getTotal()))
			.forEach(v -> addItem("damageType", options, v));
		
		repo.countTotalSpellByConcentration().stream()
			.map(c -> new Item(c.getField() ? "Есть" : "Нет", String.valueOf(c.getField()), c.getTotal(), c.getTotal()))
			.forEach(v -> addItem("concentration", options, v));
		
		repo.countTotalSpellByRitual().stream()
			.map(c -> new Item(c.getField() ? "Да" : "Нет", String.valueOf(c.getField()), c.getTotal(), c.getTotal()))
			.forEach(v -> addItem("ritual", options, v));

		repo.countTotalSpellByBook(settings == null ? EnumSet.of(TypeBook.OFFICAL) : settings.getTypeBooks()).stream()
			.map(c -> new Item(c.getField().getSource(), c.getField().getSource(), c.getTotal(), c.getTotal()))
			.forEach(v -> addItem("book", options, v));
		
		SearchPanes sPanes = new SearchPanes(options);
		output.setSearchPanes(sPanes);
		return output;
	}

	private Specification<Spell> addSpecification(Specification<Spell> specification,
			Specification<Spell> addSpecification) {
		if (specification == null) {
			return Specification.where(addSpecification);
		}
		return specification.and(addSpecification);
	}

	private void addItem(String key, Map<String, List<Item>> options, Item v) {
		options.computeIfAbsent(key, s -> new ArrayList<>()).add(v);
	}

	private Specification<Spell> bySources(Set<TypeBook> types) {
		return (root, query, cb) -> root.get("book").get("type").in(types);
	}
}