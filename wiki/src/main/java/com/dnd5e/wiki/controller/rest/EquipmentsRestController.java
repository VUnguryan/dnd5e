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

import com.dnd5e.wiki.dto.EquipmentDto;
import com.dnd5e.wiki.dto.user.Setting;
import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.creature.DamageType;
import com.dnd5e.wiki.model.spell.Spell;
import com.dnd5e.wiki.model.stock.Equipment;
import com.dnd5e.wiki.model.stock.EquipmentType;
import com.dnd5e.wiki.repository.EquipmentRepository;
import com.dnd5e.wiki.util.SourceUtil;

@RestController
public class EquipmentsRestController {
	@Autowired
	private HttpSession session;

	@Autowired
	private EquipmentRepository repo;

	@GetMapping("/data/equipments")
	public DataTablesOutput<EquipmentDto> getData(@Valid DataTablesInput input, @RequestParam Map<String, String> queryParameters) {
		Setting settings = (Setting) session.getAttribute(SettingRestController.SETTINGS);
		Set<TypeBook> sources = SourceUtil.getSources(settings);
		Specification<Equipment> specification = bySources(sources);
		input.parseSearchPanesFromQueryParams(queryParameters, Arrays.asList("type", "book"));

		List<EquipmentType> filterTypes = input.getSearchPanes().getOrDefault("type", Collections.emptySet())
			.stream()
			.map(EquipmentType::valueOf)
			.collect(Collectors.toList());
		if (!filterTypes.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> {
				Join<DamageType, Spell> types = root.join("types", JoinType.LEFT);
				query.distinct(true);
				return types.in(filterTypes);
			});
		}
		Set<String> filterBooks = input.getSearchPanes().getOrDefault("book", Collections.emptySet());
		if (!filterBooks.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("book").get("source").in(filterBooks));
		}
		input.getSearchPanes().clear();
		DataTablesOutput<EquipmentDto> output = repo.findAll(input, specification, specification,
				i -> new EquipmentDto(i));

		Map<String, List<Item>> options = new HashMap<>();

		repo.countTotalEquipmentByType().stream().filter(o -> o.getField() != null).map(
				c -> new Item(c.getField().getCyrilicName(), c.getField().name(), c.getTotal(), c.getTotal()))
				.forEach(v -> addItem("type", options, v));

		repo.countTotalEquipmentByBook().stream()
				.map(c -> new Item(c.getField().getSource(), c.getField().getSource(), c.getTotal(), c.getTotal()))
				.forEach(v -> addItem("book", options, v));

		SearchPanes sPanes = new SearchPanes(options);
		output.setSearchPanes(sPanes);
		return output;
	}

	private <T> Specification<T> addSpecification(Specification<T> specification, Specification<T> addSpecification) {
		if (specification == null) {
			return Specification.where(addSpecification);
		}
		return specification.and(addSpecification);
	}
	
	private <T> Specification<T> bySources(Set<TypeBook> types) {
		return (root, query, cb) -> root.get("book").get("type").in(types);
	}

	private void addItem(String key, Map<String, List<Item>> options, Item v) {
		options.computeIfAbsent(key, s -> new ArrayList<>()).add(v);
	}
}