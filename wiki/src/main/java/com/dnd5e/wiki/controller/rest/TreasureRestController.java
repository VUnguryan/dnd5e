package com.dnd5e.wiki.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.dnd5e.wiki.dto.TreasureDto;
import com.dnd5e.wiki.model.treasure.Treasure;
import com.dnd5e.wiki.model.treasure.TreasureType;
import com.dnd5e.wiki.repository.datatable.TreasuresRepository;

@RestController
public class TreasureRestController {
	@Autowired
	private TreasuresRepository repo;

	@GetMapping("/treasures")
	public SearchPanesOutput<TreasureDto> getData(@Valid DataTablesInput input,
			@RequestParam Map<String, String> searchPanes) {

		List<TreasureType> filterTypes = new ArrayList<>();
		for (int j = 0; j <= TreasureType.values().length; j++) {
			String type = searchPanes.get("searchPanes.type." + j);
			if (type != null) {
				filterTypes.add(TreasureType.parse(type));
			}
		}
		Specification<Treasure> specification = null;

		if (!filterTypes.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("type").in(filterTypes));
		}

		DataTablesOutput<TreasureDto> output = repo.findAll(input, specification, specification,
				i -> new TreasureDto(i));
		SearchPanes sPanes = new SearchPanes();
		Map<String, List<Item>> options = new HashMap<>();

		repo.countTotalType().stream().map(
				c -> new Item(c.getField().getName(), c.getTotal(), c.getField().name(), c.getTotal()))
				.forEach(v -> addItem("type", options, v));

		sPanes.setOptions(options);
		SearchPanesOutput<TreasureDto> spOutput = new SearchPanesOutput<>(output);
		spOutput.setSearchPanes(sPanes);
		return spOutput;
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