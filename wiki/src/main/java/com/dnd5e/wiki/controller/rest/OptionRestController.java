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
import javax.servlet.http.HttpSession;
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

import com.dnd5e.wiki.dto.OptionDto;
import com.dnd5e.wiki.dto.user.Setting;
import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.hero.Option;
import com.dnd5e.wiki.model.hero.Option.OptionType;
import com.dnd5e.wiki.model.spell.GroupByCount;
import com.dnd5e.wiki.repository.datatable.OptionDatatableRepository;
import com.dnd5e.wiki.util.SourceUtil;

@RestController
public class OptionRestController {
	@Autowired
	private HttpSession session;

	@Autowired
	private OptionDatatableRepository repo;

	@GetMapping("/data/options")
	public DataTablesOutput<OptionDto> getData(@Valid DataTablesInput input, @RequestParam Map<String, String> queryParameters) {
		Setting settings = (Setting) session.getAttribute(SettingRestController.SETTINGS);
		Set<TypeBook> sources = SourceUtil.getSources(settings);
		Specification<Option> specification = bySources(sources);
		input.parseSearchPanesFromQueryParams(queryParameters, Arrays.asList("optionTypes", "prerequisite", "book"));

		List<OptionType> optionTypes = input.getSearchPanes().getOrDefault("optionTypes", Collections.emptySet())
			.stream()
			.map(OptionType::valueOf)
			.collect(Collectors.toList());
		if (!optionTypes.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) ->{
				Join<OptionType, Option> optionType = root.join("optionTypes", JoinType.LEFT);
				return optionType.in(optionTypes);
			}); 
		}
		Set<String> prerequisites = input.getSearchPanes().getOrDefault("prerequisite", Collections.emptySet());
		if (!prerequisites.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("prerequisite").in(prerequisites));
		}
		List<Book> filterBooks = input.getSearchPanes().getOrDefault("book", Collections.emptySet())
				.stream()
				.map(Book::new)
				.collect(Collectors.toList());
		if (!filterBooks.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("book").in(filterBooks));
		}
		input.getSearchPanes().remove("optionTypes");
		input.getSearchPanes().remove("prerequisite");
		input.getSearchPanes().remove("book");
		
		DataTablesOutput<OptionDto> output = repo.findAll(input, specification, specification, i -> new OptionDto(i));

		Map<String, List<Item>> options = new HashMap<>();
		
		List<GroupByCount<OptionType>> groupOptionTypes = repo.countTotalOptionType();
		groupOptionTypes.stream()
			.map(c -> new Item(c.getField().getName(), c.getField().name(), c.getTotal(), c.getTotal()))
			.forEach(v -> addItem("optionTypes", options, v));

		List<GroupByCount<String>> prerequsites;
		if (optionTypes.isEmpty()) {
			prerequsites = repo.countTotalPrerequisite();
		}
		else
		{
			prerequsites = repo.countTotalPrerequisite(optionTypes);
		}
		if (prerequsites.size() > 1) {
			prerequsites.stream()
			.map(c -> new Item(c.getField(), c.getField(), c.getTotal(), c.getTotal()))
				.forEach(v -> addItem("prerequisite", options, v));
		} 

		repo.countTotalOptionBook().stream()
			.map(c -> new Item(c.getField().getSource(), c.getField().getSource(), c.getTotal(), c.getTotal()))
				.forEach(v -> addItem("book", options, v));

		SearchPanes sPanes = new SearchPanes(options);
		output.setSearchPanes(sPanes);
		return output;
	}

	private <T> Specification<T> bySources(Set<TypeBook> types) {
		return (root, query, cb) -> root.get("book").get("type").in(types);
	}

	private <T> Specification<T> addSpecification(Specification<T> specification , Specification<T> addSpecification){
		if (specification == null) {
			return Specification.where(addSpecification);
		}
		return specification.and(addSpecification);
	}
	
	private void addItem(String key, Map<String, List<Item>> options, Item v) {
		options.computeIfAbsent(key, s -> new ArrayList<>()).add(v);
	}
}