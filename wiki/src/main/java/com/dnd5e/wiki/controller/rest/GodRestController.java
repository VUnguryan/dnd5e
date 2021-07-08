package com.dnd5e.wiki.controller.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.mapping.SearchPanes;
import org.springframework.data.jpa.datatables.mapping.SearchPanes.Item;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dnd5e.wiki.dto.GodDto;
import com.dnd5e.wiki.model.creature.Alignment;
import com.dnd5e.wiki.model.gods.Domain;
import com.dnd5e.wiki.model.gods.God;
import com.dnd5e.wiki.model.gods.GodSex;
import com.dnd5e.wiki.model.gods.Pantheon;
import com.dnd5e.wiki.model.gods.Rank;
import com.dnd5e.wiki.repository.datatable.GodDatatableRepository;

@RestController
public class GodRestController {
	@Autowired
	private GodDatatableRepository repo;

	@GetMapping("/data/gods")
	public DataTablesOutput<GodDto> getData(@Valid DataTablesInput input, @RequestParam Map<String, String> queryParameters) {
		Specification<God> specification = null;
		input.parseSearchPanesFromQueryParams(queryParameters, Arrays.asList("aligment", "domains", "sex", "pantheon", "rank"));

		List<Alignment> filterAlignments = input.getSearchPanes().getOrDefault("aligment", Collections.emptySet())
				.stream()
				.map(Alignment::valueOf)
				.collect(Collectors.toList());

		if (!filterAlignments.isEmpty()) {
			specification = addSpecification(specification,
					(root, query, cb) -> root.get("aligment").in(filterAlignments));
		}
		List<Domain> filterDomains = input.getSearchPanes().getOrDefault("domains", Collections.emptySet())
				.stream()
				.map(Domain::valueOf)
				.collect(Collectors.toList());
		if (!filterDomains.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> {
				Join<Domain, God> domain = root.join("domains", JoinType.LEFT);
				query.distinct(true);
				return domain.in(filterDomains);
			});
		}
		List<GodSex> filterSexs = input.getSearchPanes().getOrDefault("sex", Collections.emptySet())
				.stream()
				.map(GodSex::valueOf)
				.collect(Collectors.toList());	
		if (!filterSexs.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("sex").in(filterSexs));
		}
		Set<Integer> filterPantheons = input.getSearchPanes().getOrDefault("pantheon", Collections.emptySet())
				.stream()
				.map(Integer::valueOf)
				.collect(Collectors.toSet());
		if (!filterPantheons.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> {
				Join<Pantheon, God> pantheon = root.join("pantheon", JoinType.LEFT);
				return cb.and(pantheon.get("id").in(filterPantheons));
			});
		}
		List<Rank> filterRanks = input.getSearchPanes().getOrDefault("rank", Collections.emptySet())
				.stream()
				.map(Rank::valueOf)
				.collect(Collectors.toList());
		if (!filterRanks.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("rank").in(filterRanks));
		}
		input.getSearchPanes().clear();
		DataTablesOutput<GodDto> output = repo.findAll(input, specification, specification,	i -> new GodDto(i));
		
		Map<String, List<Item>> options = new HashMap<>();
		repo.countTotalGodAlignment().stream().map(
				c -> new Item(c.getField().getCyrilicName(), c.getField().name(), c.getTotal(), c.getTotal()))
				.forEach(v -> addItem("aligment", options, v));
		repo.countTotalGodDomain().stream().map(
				c -> new Item(c.getField().getCyrilicName(), c.getField().name(), c.getTotal(), c.getTotal()))
				.forEach(v -> addItem("domains", options, v));
		repo.countTotalGodPantheon().stream().map(
				c -> new Item(c.getField().getName(), String.valueOf(c.getField().getId()), c.getTotal(), c.getTotal()))
				.forEach(v -> addItem("pantheon", options, v));
		repo.countTotalGodSex().stream().map(
				c -> new Item(c.getField().getCyrilicName(), c.getField().name(), c.getTotal(), c.getTotal()))
				.forEach(v -> addItem("sex", options, v));
		repo.countTotalGodRank().stream().map(
				c -> new Item(c.getField().getName(), c.getField().name(), c.getTotal(), c.getTotal()))
				.forEach(v -> addItem("rank", options, v));
		SearchPanes sPanes = new SearchPanes(options);
		output.setSearchPanes(sPanes);
		return output;
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