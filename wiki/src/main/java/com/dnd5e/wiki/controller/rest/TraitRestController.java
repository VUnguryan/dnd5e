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

import com.dnd5e.wiki.dto.TraitDto;
import com.dnd5e.wiki.dto.user.Setting;
import com.dnd5e.wiki.model.AbilityType;
import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.creature.SkillType;
import com.dnd5e.wiki.model.hero.Trait;
import com.dnd5e.wiki.repository.datatable.TraitDatatableRepository;
import com.dnd5e.wiki.util.SourceUtil;

@RestController
public class TraitRestController {
	@Autowired
	private HttpSession session;

	@Autowired
	private TraitDatatableRepository repo;

	@GetMapping("/data/traits")
	public DataTablesOutput<TraitDto> getData(@Valid DataTablesInput input, @RequestParam Map<String, String> queryParameters) {
		Setting settings = (Setting) session.getAttribute(SettingRestController.SETTINGS);
		Set<TypeBook> sources = SourceUtil.getSources(settings);
		Specification<Trait> specification = bySources(sources);
		input.parseSearchPanesFromQueryParams(queryParameters, Arrays.asList("abilities", "skills","requirement", "book"));

		if (input.getSearchPanes() != null) {
			List<AbilityType> filterAbylities = input.getSearchPanes().getOrDefault("abilities", Collections.emptySet())
				.stream()
				.map(AbilityType::valueOf)
				.collect(Collectors.toList());
			if (!filterAbylities.isEmpty()) {
				specification = addSpecification(specification, (root, query, cb) -> {
					Join<AbilityType, Trait> abilityType = root.join("abilities", JoinType.LEFT);
					query.distinct(true);
					return cb.and(abilityType.in(filterAbylities));
				});
			}
			List<SkillType> filterSkills = input.getSearchPanes().getOrDefault("skills", Collections.emptySet())
					.stream()
					.map(SkillType::valueOf)
					.collect(Collectors.toList());
			if (!filterSkills.isEmpty()) {
				specification = addSpecification(specification, (root, query, cb) -> {
					Join<AbilityType, Trait> abilityType = root.join("skills", JoinType.LEFT);
					query.distinct(true);
					return cb.and(abilityType.in(filterSkills));
				});
			}
			Set<String> requirements = input.getSearchPanes().getOrDefault("requirement", Collections.emptySet());
			if (!requirements.isEmpty()) {
				specification = addSpecification(specification, (root, query, cb) -> root.get("requirement").in(requirements));
			}
			Set<String> filterBooks = input.getSearchPanes().getOrDefault("book", Collections.emptySet());
			if (!filterBooks.isEmpty()) {
				specification = addSpecification(specification, (root, query, cb) -> root.get("book").get("source").in(filterBooks));
			}
		}
		input.getSearchPanes().remove("abilities");
		input.getSearchPanes().remove("skills");
		input.getSearchPanes().remove("requirement");
		input.getSearchPanes().remove("book");

		DataTablesOutput<TraitDto> output = repo.findAll(input, specification, specification, i -> new TraitDto(i));
		
		Map<String, List<Item>> options = new HashMap<>();
		repo.countTotalTraitAbilyty().stream().map(
				c -> new Item(c.getField().getCyrilicName(), String.valueOf(c.getField()), c.getTotal(), c.getTotal()))
				.forEach(v -> addItem("abilities", options, v));

		repo.countTotalTraitSkill().stream().map(
				c -> new Item(c.getField().getCyrilicName(), String.valueOf(c.getField()), c.getTotal(), c.getTotal()))
				.forEach(v -> addItem("skills", options, v));
		
		repo.countTotalRequirement().stream().map(
				c -> new Item(c.getField(), c.getField(), c.getTotal(), c.getTotal()))
				.forEach(v -> addItem("requirement", options, v));

		repo.countTotalTraitBook().stream().map(
				c -> new Item(c.getField().getSource(), c.getField().getSource(), c.getTotal(), c.getTotal()))
				.forEach(v -> addItem("book", options, v));
		SearchPanes sPanes = new SearchPanes(options);
		output.setSearchPanes(sPanes);
		return output;
	}

	private Specification<Trait> bySources(Set<TypeBook> types) {
		return (root, query, cb) -> root.get("book").get("type").in(types);
	}

	private Specification<Trait> addSpecification(Specification<Trait> specification , Specification<Trait> addSpecification){
		if (specification == null) {
			return Specification.where(addSpecification);
		}
		return specification.and(addSpecification);
	}
	
	private void addItem(String key, Map<String, List<Item>> options, Item v) {
		options.computeIfAbsent(key, s -> new ArrayList<>()).add(v);
	}
}