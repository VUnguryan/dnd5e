package com.dnd5e.wiki.controller.rest;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.dnd5e.wiki.dto.ConditionDto;
import com.dnd5e.wiki.model.hero.Condition;
import com.dnd5e.wiki.repository.datatable.ConditionDatatableRepository;

@RestController
public class ConditionRestController {
	@Autowired
	private ConditionDatatableRepository conditionRepo;
	
	@GetMapping("/conditions")
	public DataTablesOutput<ConditionDto> getData(@Valid DataTablesInput input, @RequestParam Map<String, String> searchPanes) {
		List<Condition.Type> types = new ArrayList<>();
		for (int j = 0; j < Condition.Type.values().length; j++) {
			String type = searchPanes.get("searchPanes.type." + j);
			if (type != null) {
				types.add(Condition.Type.parse(type));
			}
		}
		Specification<Condition> specification = null;
		if (!types.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("type").in(types));
		}
		SearchPanes sPanes = new SearchPanes();
		Map<String, List<Item>> options = new HashMap<>();
		Arrays.asList(Condition.Type.values())
			.forEach(condition -> addItem("type", options, new Item<String>(condition.getName(),0L, condition.getName(), 0L)));
		
		DataTablesOutput<ConditionDto> output = conditionRepo.findAll(input, null, specification, ConditionDto::new);
		sPanes.setOptions(options); 
		SearchPanesOutput<ConditionDto> spOutput = new SearchPanesOutput<>(output);
		spOutput.setSearchPanes(sPanes);
		return spOutput;
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