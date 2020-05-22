package com.dnd5e.wiki.controller.rest;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dnd5e.wiki.controller.rest.paging.SearchPanesOutput;
import com.dnd5e.wiki.dto.user.TraitDto;
import com.dnd5e.wiki.model.hero.Trait;
import com.dnd5e.wiki.repository.TraitDataTablesRepository;
import com.dnd5e.wiki.repository.TraitRepository;

@RestController
public class TraitRestController {
	@Autowired
	private TraitRepository repo;
	
	@Autowired
	private TraitDataTablesRepository traitRepo;

	@PostMapping("/traits")
	public DataTablesOutput<Trait> getData(@RequestBody DataTablesInput input) {
		return traitRepo.findAll(input);
	}

	public SearchPanesOutput<TraitDto> getTraits(@RequestBody DataTablesInput input){
		int pageNumber =  input.getStart() / input.getLength();

		Pageable pageRequest = PageRequest.of(pageNumber, input.getLength());
		Specification<Trait> specification = null;

		if (input.getSearch().getValue() != null && !input.getSearch().getValue().isEmpty())
		{
			specification = byName(input.getSearch().getValue());
		}
		Page<Trait> traits;
		if (specification!= null) {
			traits = repo.findAll(specification, pageRequest);
		}
		else
		{
			traits = repo.findAll(pageRequest);
		}
		
		SearchPanesOutput<TraitDto> page = new SearchPanesOutput<>(traits.getContent().stream().map(TraitDto::new).collect(Collectors.toList()));
		page.setRecordsTotal(traits.getTotalPages() * input.getLength());
		page.setRecordsFiltered(traits.getTotalPages() * input.getLength());
		page.setDraw(input.getDraw());
		return page;
	}
	
	private Specification<Trait> byName(String search) {
		return (root, query, cb) -> cb.or(cb.like(root.get("name"), "%" + search + "%"),
				cb.like(root.get("requirement"), "%" + search + "%"));
	}
}