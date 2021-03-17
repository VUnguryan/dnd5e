package com.dnd5e.wiki.controller.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dnd5e.wiki.controller.rest.model.json.foundary.FCreature;
import com.dnd5e.wiki.controller.rest.paging.Item;
import com.dnd5e.wiki.controller.rest.paging.SearchPanes;
import com.dnd5e.wiki.controller.rest.paging.SearchPanesOutput;
import com.dnd5e.wiki.dto.CreatureDto;
import com.dnd5e.wiki.dto.user.Setting;
import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.creature.Action;
import com.dnd5e.wiki.model.creature.ActionType;
import com.dnd5e.wiki.model.creature.Creature;
import com.dnd5e.wiki.model.creature.CreatureFeat;
import com.dnd5e.wiki.model.creature.CreatureSize;
import com.dnd5e.wiki.model.creature.CreatureType;
import com.dnd5e.wiki.model.hero.Option;
import com.dnd5e.wiki.model.hero.Option.OptionType;
import com.dnd5e.wiki.model.spell.GroupByCount;
import com.dnd5e.wiki.repository.datatable.CreatureDatatableRepository;
import com.dnd5e.wiki.util.SourceUtil;

@RestController
public class CreatureRestController {
	@Autowired
	private HttpSession session;
	
	@Autowired
	private CreatureDatatableRepository repo;
	
	@GetMapping("/creatures")
	public DataTablesOutput<CreatureDto> getData(@Valid DataTablesInput input, @RequestParam Map<String, String> searchPanes) {
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
		DataTablesOutput<CreatureDto> output;
		Specification<Creature> specification = bySources(sources);
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
		for (int i = 0; i < 4; i++) {
			if("Логово".equals(searchPanes.get("searchPanes.legendary." + i))) {
				specification = addSpecification(specification, (root, query, cb) -> root.get("lair").isNotNull());
				break;
			}
		}
		for (int i = 0; i < 4; i++) {
			if("Легендарные действия".equals(searchPanes.get("searchPanes.legendary." + i))) {
				specification = addSpecification(specification, (root, query, cb) -> {
					Join<Action, Creature> actions = root.join("actions", JoinType.LEFT);
					query.distinct(true);
					return actions.get("actionType").in(Collections.singleton(ActionType.LEGENDARY));
				});
				break;
			}
		}
		for (int i = 0; i < 4; i++) {
			if("Колдовство".equals(searchPanes.get("searchPanes.legendary." + i))) {
				specification = addSpecification(specification, (root, query, cb) -> {
					Join<CreatureFeat, Creature> feats = root.join("feats", JoinType.LEFT);
					query.distinct(true);
					return feats.get("name").in(Arrays.asList("Использование заклинаний", "Колдовство"));
				});
				break;
			}
		}
		for (int i = 0; i < 4; i++) {
			if("Врожденное колдовство".equals(searchPanes.get("searchPanes.legendary." + i))) {
				specification = addSpecification(specification, (root, query, cb) -> {
					Join<CreatureFeat, Creature> feats = root.join("feats", JoinType.LEFT);
					query.distinct(true);
					return cb.like(feats.get("name"), "Врожденное колдовство%");
				});
				break;
			}
		}
		output = repo.findAll(input, null, specification, creature -> new CreatureDto(creature));
		
		SearchPanes sPanes = new SearchPanes();
		Map<String, List<Item>> options = new HashMap<>();

		repo.countTotalCreatureByCr().stream().map(this::convertCr).forEach(v -> addItem("exp", options, v));

		repo.countTotalCreatureByType().stream() .map(c -> new
				  Item(String.valueOf(c.getField().getCyrilicName()), c.getTotal(),
				  String.valueOf(c.getField()), c.getTotal())) .forEach(v -> addItem("type", options, v));
		repo.countTotalCreatureBySize().stream() .map(c -> new
				  Item(String.valueOf(c.getField().getCyrilicName()), c.getTotal(),
				  String.valueOf(c.getField()), c.getTotal())) .forEach(v -> addItem("size", options, v));
		
		repo.countTotalCreatureByBook(settings == null ? EnumSet.of(TypeBook.OFFICAL) : settings.getTypeBooks()).stream() .map(c -> new
				  Item(String.valueOf(c.getField().getSource()), c.getTotal(),
				  String.valueOf(c.getField()), c.getTotal())) .forEach(v -> addItem("book", options, v));

		addItem("legendary", options, new Item<String>("Колдовство", 0L, "Колдовство", 0L));
		addItem("legendary", options, new Item<String>("Врожденное колдовство", 0L, "Врожденное колдовство", 0L));
		addItem("legendary", options, new Item<String>("Легендарные действия", 0L, "Легендарные действия", 0L));
		addItem("legendary", options, new Item<String>("Логово", 0L, "Логово", 0L));

		sPanes.setOptions(options); 
		SearchPanesOutput<CreatureDto> spOutput = new SearchPanesOutput<>(output);
		spOutput.setSearchPanes(sPanes);
		return spOutput;
	}
	
	@GetMapping("/creatures/{id}")
	public FCreature getCreature(@PathVariable Integer id)
	{
		Creature creature = repo.findById(id).get();
		FCreature fcreature = new FCreature(creature);
		return fcreature;
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
	
	private Specification<Creature> bySources(Set<TypeBook> types) {
		return (root, query, cb) -> root.get("book").get("type").in(types);
	}
}