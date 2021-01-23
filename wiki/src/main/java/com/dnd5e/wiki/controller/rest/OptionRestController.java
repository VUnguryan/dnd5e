package com.dnd5e.wiki.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.servlet.http.HttpSession;
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
import com.dnd5e.wiki.dto.OptionDto;
import com.dnd5e.wiki.dto.user.Setting;
import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.hero.Option;
import com.dnd5e.wiki.model.hero.Option.OptionType;
import com.dnd5e.wiki.model.hero.Trait;
import com.dnd5e.wiki.repository.datatable.OptionDatatableRepository;
import com.dnd5e.wiki.util.SourceUtil;

@RestController
public class OptionRestController {
	@Autowired
	private HttpSession session;

	@Autowired
	private OptionDatatableRepository repo;

	@GetMapping("/options")
	public SearchPanesOutput<OptionDto> getData(@Valid DataTablesInput input, @RequestParam Map<String, String> searchPanes) {
		Setting settings = (Setting) session.getAttribute(SettingRestController.SETTINGS);
		Set<TypeBook> sources = SourceUtil.getSources(settings);

		List<Book> filterBooks = new ArrayList<>();
		for (int j = 0; j <= 21; j++) {
			String type = searchPanes.get("searchPanes.book." + j);
			if (type != null) {
				Book book = new Book();
				book.setSource(type);
				filterBooks.add(book);
			}
		}
		List<OptionType> filterTypes = new ArrayList<>();
		for (int j = 0; j <= 21; j++) {
			String type = searchPanes.get("searchPanes.optionType." + j);
			if (type != null) {
				filterTypes.add(OptionType.parse(type));
			}
		}
		Specification<Option> specification = bySources(sources);
		if (!filterBooks.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("book").in(filterBooks));
		}
		if (!filterTypes.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("optionType").in(filterTypes));
		}
		DataTablesOutput<OptionDto> output = repo.findAll(input, specification, specification,
				i -> new OptionDto(i));
		SearchPanes sPanes = new SearchPanes();
		Map<String, List<Item>> options = new HashMap<>();

		repo.countTotalOptionBook().stream().map(
				c -> new Item(c.getField().getSource(), c.getTotal(), String.valueOf(c.getField()), c.getTotal()))
				.forEach(v -> addItem("book", options, v));
		repo.countTotalOptionType().stream().map(
				c -> new Item(c.getField().getName(), c.getTotal(), String.valueOf(c.getField()), c.getTotal()))
				.forEach(v -> addItem("optionType", options, v));

		sPanes.setOptions(options);
		SearchPanesOutput<OptionDto> spOutput = new SearchPanesOutput<>(output);
		spOutput.setSearchPanes(sPanes);
		return spOutput;
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