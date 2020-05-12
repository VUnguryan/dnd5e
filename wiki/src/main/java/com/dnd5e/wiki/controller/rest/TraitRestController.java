package com.dnd5e.wiki.controller.rest;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dnd5e.wiki.controller.rest.paging.PageResult;
import com.dnd5e.wiki.controller.rest.paging.PagingRequest;
import com.dnd5e.wiki.dto.user.TraitDto;
import com.dnd5e.wiki.model.hero.Trait;
import com.dnd5e.wiki.repository.TraitRepository;

@RestController
public class TraitRestController {
	@Autowired
	private TraitRepository repo;
	
	@PostMapping("/traits")
	public PageResult<TraitDto> getTraits(@RequestBody PagingRequest pagingRequest){
		int pageNumber =  pagingRequest.getStart() / pagingRequest.getLength();

		Pageable pageRequest = PageRequest.of(pageNumber, pagingRequest.getLength());
		Specification<Trait> specification = null;

		if (pagingRequest.getSearch().getValue() != null && !pagingRequest.getSearch().getValue().isEmpty())
		{
			specification = byName(pagingRequest.getSearch().getValue());
		}
		Page<Trait> traits;
		if (specification!= null) {
			traits = repo.findAll(specification, pageRequest);
		}
		else
		{
			traits = repo.findAll(pageRequest);
		}
		
		PageResult<TraitDto> page = new PageResult<>(traits.getContent().stream().map(TraitDto::new).collect(Collectors.toList()));
		page.setRecordsTotal(traits.getTotalPages() * pagingRequest.getLength());
		page.setRecordsFiltered(traits.getTotalPages() * pagingRequest.getLength());
		page.setDraw(pagingRequest.getDraw());
		return page;
	}
	
	private Specification<Trait> byName(String search) {
		return (root, query, cb) -> cb.or(cb.like(root.get("name"), "%" + search + "%"),
				cb.like(root.get("requirement"), "%" + search + "%"));
	}
}