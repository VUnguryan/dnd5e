package com.dnd5e.wiki.controller.rest;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd5e.wiki.dto.RaceDto;
import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.hero.race.Race;
import com.dnd5e.wiki.model.spell.Spell;
import com.dnd5e.wiki.repository.datatable.RaceDatatableRepository;

@RestController
public class RaceRestController {
	@Autowired
	private RaceDatatableRepository repo;

	@GetMapping("/table/races")
	public DataTablesOutput<RaceDto> getData(@Valid DataTablesInput input) {
		Specification<Race> specification = specification = byOfficial();
		specification = addSpecification(specification, (root, query, cb) -> root.get("parent").isNull());
		return repo.findAll(input, specification, specification, RaceDto::new);
	}

	private <T> Specification<T> addSpecification(Specification<T> specification , Specification<T> addSpecification){
		if (specification == null) {
			return Specification.where(addSpecification);
		}
		return specification.and(addSpecification);
	}

	private Specification<Race> byOfficial() {
		return (root, query, cb) -> {
			Join<Book, Spell> hero = root.join("book", JoinType.LEFT);
			return cb.equal(hero.get("type"), TypeBook.OFFICAL);
		};	
	}
}