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

import com.dnd5e.wiki.dto.ConditionDto;
import com.dnd5e.wiki.model.hero.Condition;
import com.dnd5e.wiki.repository.datatable.ConditionDatatableRepository;

@RestController
public class ConditionRestController {
	@Autowired
	private ConditionDatatableRepository conditionRepo;
	
	@GetMapping("/conditions")
	public DataTablesOutput<ConditionDto> getData(@Valid DataTablesInput input, @RequestParam Map<String, String> queryParameters) {
		Specification<Condition> specification = null;
		input.parseSearchPanesFromQueryParams(queryParameters, Collections.singleton("type"));

		if (input.getSearchPanes() != null) {
			List<Condition.Type> types = input.getSearchPanes().getOrDefault("type", Collections.emptySet())
				.stream()
				.map(Condition.Type::valueOf)
				.collect(Collectors.toList());
			if (!types.isEmpty()) {
				specification = addSpecification(specification, (root, query, cb) -> root.get("type").in(types));
			}
		}
		input.getSearchPanes().remove("type");
		Map<String, List<Item>> options = new HashMap<>();
		Arrays.asList(Condition.Type.values())
			.forEach(condition -> addItem("type", options, new Item(condition.getName(), condition.name(), 0L, 0L)));
		
		DataTablesOutput<ConditionDto> output = conditionRepo.findAll(input, null, specification, ConditionDto::new);
		SearchPanes sPanes = new SearchPanes(options);
		output.setSearchPanes(sPanes);
		return output;
	}

	private void addItem(String key, Map<String, List<Item>> options, Item v) {
		options.computeIfAbsent(key, s -> new ArrayList<>()).add(v);
	}

	private <T> Specification<T> addSpecification(Specification<T> specification , Specification<T> addSpecification){
		if (specification == null) {
			return Specification.where(addSpecification);
		}
		return specification.and(addSpecification);
	}
}