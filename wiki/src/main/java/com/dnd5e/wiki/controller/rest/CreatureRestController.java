package com.dnd5e.wiki.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.dnd5e.wiki.dto.CreatureDto;
import com.dnd5e.wiki.dto.user.Setting;
import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.creature.Creature;
import com.dnd5e.wiki.model.creature.CreatureSize;
import com.dnd5e.wiki.model.creature.CreatureType;
import com.dnd5e.wiki.model.spell.GroupByCount;
import com.dnd5e.wiki.repository.datatable.CreatureDatatableRepository;

@RestController
public class CreatureRestController {
	@Autowired
	private HttpSession session;
	
	@Autowired
	private CreatureDatatableRepository repo;
	
	@GetMapping("/creatures")
	public DataTablesOutput<CreatureDto> getData(@Valid DataTablesInput input, @RequestParam Map<String, String> searchPanes) {
		Setting setting = (Setting) session.getAttribute(SettingRestController.HOME_RULE);

		List<Book> filterBooks = new ArrayList<>();
		for (int j = 0; j <= 21; j++) {
			String type = searchPanes.get("searchPanes.book." + j);
			if (type != null) {
				Book book = new Book();
				book.setSource(type);
				filterBooks.add(book);
			}
		}
		DataTablesOutput<CreatureDto> output;
		Specification<Creature> specification = null;
		if (setting == null || !setting.isHomeRule())
		{
			specification = byOfficial();
		}
		List<String> filterCr = new ArrayList<>();
		for (int j = 0; j <= 34; j++) {
			String cr = searchPanes.get("searchPanes.exp." + j);
			if (cr != null) {
				if (cr.equals("0.5")) {
					filterCr.add("1/2");
				} else if (cr.equals("0.25")) {
					filterCr.add("1/4");
				} else if (cr.equals("0.125")) {
					filterCr.add("1/8");
				} else {
					filterCr.add(cr);
				}
			}
		}
		if (!filterCr.isEmpty()) {
			specification = addSpecification(specification,  (root, query, cb) -> root.get("challengeRating").in(filterCr));
		}
		List<CreatureType> filterTypes = new ArrayList<>();
		for (int j = 0; j <= CreatureType.values().length; j++) {
			String type = searchPanes.get("searchPanes.type." + j);
			if (type != null) {
				filterTypes.add(CreatureType.parse(type));
			}
		}
		if (!filterTypes.isEmpty()) {
			specification = addSpecification(specification,  (root, query, cb) -> root.get("type").in(filterTypes));
		}
		List<CreatureSize> filterSizes = new ArrayList<>();
		for (int j = 0; j <= CreatureSize.values().length; j++) {
			String size = searchPanes.get("searchPanes.size." + j);
			if (size != null) {
				filterSizes.add(CreatureSize.parse(size));
			}
		}
		if (!filterSizes.isEmpty()) {
			specification = addSpecification(specification,  (root, query, cb) -> root.get("size").in(filterSizes));
		}
		if (!filterBooks.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("book").in(filterBooks));
		}
		output = repo.findAll(input, null, specification, i -> new CreatureDto(i));
		
		SearchPanes sPanes = new SearchPanes();
		Map<String, List<Item>> options = new HashMap<>();

		repo.countTotalCreatureByCr().stream().map(this::convertCr).forEach(v -> addItem("exp", options, v));

		repo.countTotalCreatureByType().stream() .map(c -> new
				  Item(String.valueOf(c.getField().getCyrilicName()), c.getTotal(),
				  String.valueOf(c.getField()), c.getTotal())) .forEach(v -> addItem("type", options, v));
		repo.countTotalCreatureBySize().stream() .map(c -> new
				  Item(String.valueOf(c.getField().getCyrilicName()), c.getTotal(),
				  String.valueOf(c.getField()), c.getTotal())) .forEach(v -> addItem("size", options, v));
		repo.countTotalCreatureByBook().stream() .map(c -> new
				  Item(String.valueOf(c.getField().getSource()), c.getTotal(),
				  String.valueOf(c.getField()), c.getTotal())) .forEach(v -> addItem("book", options, v));
		
		sPanes.setOptions(options); 
		SearchPanesOutput<CreatureDto> spOutput = new SearchPanesOutput<>(output);
		spOutput.setSearchPanes(sPanes);
		return spOutput;
	}
	
	private Item<Float> convertCr(GroupByCount<String> group) {
		float  cr = 0f;
		switch (group.getField()) {
		case "1/8":
			cr = 1/8f;
			break;
		case "1/4":
			cr = 1/4f;
			break;
		case "1/2":
			cr = 1/2f;
			break;
		default:
			cr = Float.valueOf(group.getField());
			break;
		}
		return new Item<Float>(cr, group.getTotal(), group.getField(), group.getTotal());
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
	
	private Specification<Creature> byOfficial() {
		return (root, query, cb) -> {
			Join<Book, Creature> hero = root.join("book", JoinType.LEFT);
			return cb.equal(hero.get("type"), TypeBook.OFFICAL);
		};	
	}
}