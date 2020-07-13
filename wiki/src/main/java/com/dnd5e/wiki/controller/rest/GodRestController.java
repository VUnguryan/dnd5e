package com.dnd5e.wiki.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
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
import com.dnd5e.wiki.dto.GodDto;
import com.dnd5e.wiki.model.AbilityType;
import com.dnd5e.wiki.model.creature.Alignment;
import com.dnd5e.wiki.model.gods.Domain;
import com.dnd5e.wiki.model.gods.God;
import com.dnd5e.wiki.model.gods.GodSex;
import com.dnd5e.wiki.model.gods.Pantheon;
import com.dnd5e.wiki.model.gods.Rank;
import com.dnd5e.wiki.model.hero.Trait;
import com.dnd5e.wiki.repository.datatable.GodDatatableRepository;

@RestController
public class GodRestController {
	@Autowired
	private GodDatatableRepository repo;

	@GetMapping("/gods")
	public SearchPanesOutput<GodDto> getData(@Valid DataTablesInput input, @RequestParam Map<String, String> searchPanes) {

		List<Alignment> filterAlignments = new ArrayList<>();
		for (int j = 0; j <= Alignment.values().length; j++) {
			String alignment = searchPanes.get("searchPanes.aligment." + j);
			if (alignment != null) {
				filterAlignments.add(Alignment.parse(alignment));
			}
		}
		Specification<God> specification = null;
		if (!filterAlignments.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("aligment").in(filterAlignments));
		}
		List<Domain> filterDomains = new ArrayList<>();
		for (int j = 0; j <= Domain.values().length; j++) {
			String domain = searchPanes.get("searchPanes.domains." + j);
			if (domain != null) {
				filterDomains.add(Domain.parse(domain));
			}
		}
		if (!filterDomains.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> {
				Join<Domain, God> domain = root.join("domains", JoinType.LEFT);
				query.distinct(true);
				return domain.in(filterDomains);
			});
		}
		List<GodSex> filterSexs = new ArrayList<>();
		for (int j = 0; j <= GodSex.values().length; j++) {
			String domain = searchPanes.get("searchPanes.sex." + j);
			if (domain != null) {
				filterSexs.add(GodSex.parse(domain));
			}
		}
		if (!filterSexs.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("sex").in(filterSexs));
		}
		List<String> filterPantheons = new ArrayList<>();
		for (int j = 0; j <= Alignment.values().length; j++) {
			String pantheon = searchPanes.get("searchPanes.pantheon." + j);
			if (pantheon != null) {
				filterPantheons.add(pantheon);
			}
		}
		if (!filterPantheons.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> {
				Join<Pantheon, God> pantheon = root.join("pantheon", JoinType.LEFT);
				return cb.and(pantheon.get("name").in(filterPantheons));
			});
		}
		List<Rank> filterRanks = new ArrayList<>();
		for (int j = 0; j <= Rank.values().length; j++) {
			String rank = searchPanes.get("searchPanes.rank." + j);
			if (rank != null) {
				filterRanks.add(Rank.parse(rank));
			}
		}
		if (!filterRanks.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("rank").in(filterRanks));
		}
		DataTablesOutput<GodDto> output = repo.findAll(input, specification, specification,	i -> new GodDto(i));
		
		SearchPanes sPanes = new SearchPanes();
		Map<String, List<Item>> options = new HashMap<>();
		
		repo.countTotalGodAlignment().stream().map(
				c -> new Item(c.getField().getCyrilicName(), c.getTotal(), String.valueOf(c.getField()), c.getTotal()))
				.forEach(v -> addItem("aligment", options, v));
		repo.countTotalGodDomain().stream().map(
				c -> new Item(c.getField().getCyrilicName(), c.getTotal(), String.valueOf(c.getField()), c.getTotal()))
				.forEach(v -> addItem("domains", options, v));
		repo.countTotalGodPantheon().stream().map(
				c -> new Item(c.getField().getName(), c.getTotal(), String.valueOf(c.getField()), c.getTotal()))
				.forEach(v -> addItem("pantheon", options, v));
		repo.countTotalGodSex().stream().map(
				c -> new Item(c.getField().getCyrilicName(), c.getTotal(), String.valueOf(c.getField()), c.getTotal()))
				.forEach(v -> addItem("sex", options, v));
		repo.countTotalGodRank().stream().map(
				c -> new Item(c.getField().getName(), c.getTotal(), String.valueOf(c.getField()), c.getTotal()))
				.forEach(v -> addItem("rank", options, v));
		
		sPanes.setOptions(options);
		SearchPanesOutput<GodDto> spOutput = new SearchPanesOutput<>(output);
		spOutput.setSearchPanes(sPanes);
		return spOutput;
	}

	private Specification<God> addSpecification(Specification<God> specification , Specification<God> addSpecification){
		if (specification == null) {
			return Specification.where(addSpecification);
		}
		return specification.and(addSpecification);
	}
	
	private void addItem(String key, Map<String, List<Item>> options, Item v) {
		options.computeIfAbsent(key, s -> new ArrayList<>()).add(v);
	}
}