package com.dnd5e.wiki.controller.rest;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd5e.wiki.dto.CharacterDto;
import com.dnd5e.wiki.model.tavern.UserCharacter;
import com.dnd5e.wiki.repository.datatable.UserCharacterDatatableRepository;

@RestController
public class UserCharacterRestController {
	@Autowired
	private UserCharacterDatatableRepository repo;

	@GetMapping("/table/characters")
	public DataTablesOutput<CharacterDto> getData(Principal principal, @Valid DataTablesInput input) {
		if(principal == null) {
			return new DataTablesOutput<CharacterDto>();
		}
		Specification<UserCharacter> specification = addSpecification(null,
				(root, query, cb) -> cb.equal(root.get("user").get("name"), principal.getName()));
		return repo.findAll(input, specification, specification, CharacterDto::new);
	}

	private <T> Specification<T> addSpecification(Specification<T> specification, Specification<T> addSpecification) {
		if (specification == null) {
			return Specification.where(addSpecification);
		}
		return specification.and(addSpecification);
	}
}