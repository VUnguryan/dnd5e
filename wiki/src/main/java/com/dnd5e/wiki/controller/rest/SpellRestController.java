package com.dnd5e.wiki.controller.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dnd5e.wiki.controller.rest.paging.Direction;
import com.dnd5e.wiki.controller.rest.paging.PageResult;
import com.dnd5e.wiki.controller.rest.paging.PagingRequest;
import com.dnd5e.wiki.dto.user.Setting;
import com.dnd5e.wiki.dto.user.SpellDto;
import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.spell.Spell;
import com.dnd5e.wiki.repository.SpellRepository;

@RestController
public class SpellRestController {
	@Autowired
	private SpellRepository repo;
	
	@Autowired
	private HttpSession session;
	
	@PostMapping("/spells")
	public PageResult<SpellDto> getTraits(@RequestBody PagingRequest pagingRequest){
		int pageNumber =  pagingRequest.getStart() / pagingRequest.getLength();
		List<Order> orders = pagingRequest.getOrder().stream()
				.map(o -> o.getDir() == Direction.asc ? Order.asc(pagingRequest.getColumns().get(o.getColumn()).getData()) : Order.desc(pagingRequest.getColumns().get(o.getColumn()).getData()))
				.collect(Collectors.toList());
		
		Pageable pageRequest = PageRequest.of(pageNumber, pagingRequest.getLength(), Sort.by(orders));
		Specification<Spell> specification = null;
		if (pagingRequest.getSearch().getValue() != null && !pagingRequest.getSearch().getValue().isEmpty())
		{
			specification = byName(pagingRequest.getSearch().getValue());
		}
		Setting setting = (Setting) session.getAttribute(SettingRestController.HOME_RULE);
		if (setting == null || !setting.isHomeRule())
		{
			if (specification == null) {
				specification = byOfficial();
			} else {
				specification = Specification.where(specification).and(byOfficial());
			}
		}
		Page<Spell> spells;
		if (specification!= null) {
			spells = repo.findAll(specification, pageRequest);
		}
		else
		{
			spells = repo.findAll(pageRequest);
		}
		
		PageResult<SpellDto> page = new PageResult<>(spells.getContent().stream().map(SpellDto::new).collect(Collectors.toList()));
		page.setRecordsTotal(spells.getTotalPages() * pagingRequest.getLength());
		page.setRecordsFiltered(spells.getTotalPages() * pagingRequest.getLength());
		page.setDraw(pagingRequest.getDraw());
		return page;
	}
	
	private Specification<Spell> byName(String search) {
		return (root, query, cb) -> cb.or(cb.like(root.get("name"), "%" + search + "%"),
				cb.like(root.get("englishName"), "%" + search + "%"));
	}
	
	private Specification<Spell> byOfficial() {
		return (root, query, cb) -> {
			Join<Book, Spell> hero = root.join("book", JoinType.LEFT);
			return cb.equal(hero.get("type"), TypeBook.OFFICAL);
		};	
	}
}