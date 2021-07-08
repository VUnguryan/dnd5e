package com.dnd5e.wiki.controller.rest;

import java.util.Arrays;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dnd5e.wiki.dto.RuleDto;
import com.dnd5e.wiki.model.Rule;
import com.dnd5e.wiki.repository.datatable.RuleDatatableRepository;

@RestController
public class RuleRestController {
	@Autowired
	private RuleDatatableRepository ruleRepo;
	
	@GetMapping("/data/rules")
	public DataTablesOutput<RuleDto> getData(@Valid DataTablesInput input, @RequestParam Map<String, String> queryParameters) {
		input.parseSearchPanesFromQueryParams(queryParameters, Arrays.asList("type"));
		Specification<Rule> specification = null;
		DataTablesOutput<RuleDto> output = ruleRepo.findAll(input, specification, specification, RuleDto::new);
		return output;
	}
}