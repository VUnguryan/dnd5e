package com.dnd5e.wiki.controller.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd5e.wiki.dto.ClassDto;
import com.dnd5e.wiki.repository.datatable.ClassDatatableRepository;

@RestController
public class ClassRestController {
	@Autowired
	private ClassDatatableRepository repo;

	@GetMapping("/table/classes")
	public DataTablesOutput<ClassDto> getData(@Valid DataTablesInput input) {
		return repo.findAll(input, null, null, ClassDto::new);
	}

	private <T> Specification<T> addSpecification(Specification<T> specification , Specification<T> addSpecification){
		if (specification == null) {
			return Specification.where(addSpecification);
		}
		return specification.and(addSpecification);
	}
}