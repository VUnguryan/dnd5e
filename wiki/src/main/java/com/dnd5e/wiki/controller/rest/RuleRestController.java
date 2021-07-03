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
import com.dnd5e.wiki.dto.RuleDto;
import com.dnd5e.wiki.model.Rule;
import com.dnd5e.wiki.repository.datatable.RuleDatatableRepository;

@RestController
public class RuleRestController {
	@Autowired
	private RuleDatatableRepository ruleRepo;
	
	@GetMapping("/data/rules")
	public DataTablesOutput<RuleDto> getData(@Valid DataTablesInput input, @RequestParam Map<String, String> searchPanes) {
		List<String> types = new ArrayList<>();
		for (int j = 0; j < 20; j++) {
			String type = searchPanes.get("searchPanes.type." + j);
			if (type != null) {
				types.add(type);
			}
		}
		Specification<Rule> specification = null;
		if (!types.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("type").in(types));
		}
		SearchPanes sPanes = new SearchPanes();
		Map<String, List<Item>> options = new HashMap<>();
		ruleRepo.countTotalTypes().stream() .map(c -> new
				  Item(String.valueOf(c.getField()), c.getTotal(),
				  String.valueOf(c.getField()), c.getTotal())) .forEach(v -> addItem("type", options, v));
		DataTablesOutput<RuleDto> output = ruleRepo.findAll(input, specification, specification, RuleDto::new);
		sPanes.setOptions(options); 
		SearchPanesOutput<RuleDto> spOutput = new SearchPanesOutput<>(output);
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