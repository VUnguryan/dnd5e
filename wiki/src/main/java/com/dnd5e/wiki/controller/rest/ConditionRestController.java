package com.dnd5e.wiki.controller.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd5e.wiki.model.hero.Condition;
import com.dnd5e.wiki.repository.datatable.ConditionDatatableRepository;

@RestController
public class ConditionRestController {
	@Autowired
	private ConditionDatatableRepository conditionRepo;
	
	@GetMapping("/conditions")
	public DataTablesOutput<Condition> getData(@Valid DataTablesInput input) {
		
		return conditionRepo.findAll(input);
	}
}