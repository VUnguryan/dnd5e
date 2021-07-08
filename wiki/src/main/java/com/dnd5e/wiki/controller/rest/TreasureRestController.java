package com.dnd5e.wiki.controller.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.dnd5e.wiki.dto.TreasureDto;
import com.dnd5e.wiki.model.treasure.Treasure;
import com.dnd5e.wiki.model.treasure.TreasureType;
import com.dnd5e.wiki.repository.datatable.TreasuresDatatableRepository;

@RestController
public class TreasureRestController {
	@Autowired
	private TreasuresDatatableRepository repo;

	@GetMapping("/treasures")
	public DataTablesOutput<TreasureDto> getData(@Valid DataTablesInput input, @RequestParam Map<String, String> queryParameters) {
		Specification<Treasure> specification = null;
		input.parseSearchPanesFromQueryParams(queryParameters, Arrays.asList("type"));

		List<TreasureType> filterTypes = input.getSearchPanes().getOrDefault("type", Collections.emptySet())
			.stream()
			.map(TreasureType::valueOf)
			.collect(Collectors.toList());
		if (!filterTypes.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("type").in(filterTypes));
		}
		input.getSearchPanes().clear();
		
		DataTablesOutput<TreasureDto> output = repo.findAll(input, specification, specification, TreasureDto::new);
		Map<String, List<Item>> options = new HashMap<>();

		repo.countTotalType().stream().map(
				c -> new Item(c.getField().getName(), c.getField().name(), c.getTotal(), c.getTotal()))
				.forEach(v -> addItem("type", options, v));
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

	private void addItem(String key, Map<String, List<Item>> options, Item v) {
		options.computeIfAbsent(key, s -> new ArrayList<>()).add(v);
	}
}