package com.dnd5e.wiki.controller.rest;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dnd5e.wiki.controller.rest.model.json.foundary.FCreature;
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
import com.dnd5e.wiki.model.spell.GroupByCount;
import com.dnd5e.wiki.repository.datatable.CreatureDatatableRepository;
import com.dnd5e.wiki.util.SourceUtil;

@RestController
public class CreatureRestController {
	@Autowired
	private HttpSession session;
	
	@Autowired
	private CreatureDatatableRepository repo;
	
	@GetMapping("/data/creatures")
	public DataTablesOutput<CreatureDto> getData(@Valid DataTablesInput input, @RequestParam Map<String, String> queryParameters) {
		Setting settings = (Setting) session.getAttribute(SettingRestController.SETTINGS);
		Set<TypeBook> sources = SourceUtil.getSources(settings);
		Specification<Creature> specification = bySources(sources);
		
		input.parseSearchPanesFromQueryParams(queryParameters, Arrays.asList("exp", "type", "size", "legendary", "book"));
		
		List<String> filterCr = input.getSearchPanes().getOrDefault("exp", Collections.emptySet())
			.stream()
			.map(cr -> parseCr(cr))
			.collect(Collectors.toList());
		if (!filterCr.isEmpty()) {
			specification = addSpecification(specification,  (root, query, cb) -> root.get("challengeRating").in(filterCr));
		}
		List<CreatureType> filterTypes = input.getSearchPanes().getOrDefault("type", Collections.emptySet())
				.stream()
				.map(CreatureType::valueOf)
				.collect(Collectors.toList());
		if (!filterTypes.isEmpty()) {
			specification = addSpecification(specification,  (root, query, cb) -> root.get("type").in(filterTypes));
		}
		List<CreatureSize> filterSizes = input.getSearchPanes().getOrDefault("size", Collections.emptySet())
				.stream()
				.map(CreatureSize::valueOf)
				.collect(Collectors.toList());
		if (!filterSizes.isEmpty()) {
			specification = addSpecification(specification,  (root, query, cb) -> root.get("size").in(filterSizes));
		}

		Set<String> actions = input.getSearchPanes().getOrDefault("legendary", Collections.emptySet());
		if(actions.contains("Логово")) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("lair").isNotNull());
		}
		if(actions.contains("Легендарные действия")) {
			specification = addSpecification(specification, (root, query, cb) -> {
				Join<Action, Creature> joinActions = root.join("actions", JoinType.LEFT);
				query.distinct(true);
				return joinActions.get("actionType").in(Collections.singleton(ActionType.LEGENDARY));
			});
		}
		if(actions.contains("Колдовство")) {
			specification = addSpecification(specification, (root, query, cb) -> {
				Join<CreatureFeat, Creature> feats = root.join("feats", JoinType.LEFT);
				query.distinct(true);
				return feats.get("name").in(Arrays.asList("Использование заклинаний", "Колдовство"));
			});
		}
		if(actions.contains("Врожденное колдовство")) {
			specification = addSpecification(specification, (root, query, cb) -> {
				Join<CreatureFeat, Creature> feats = root.join("feats", JoinType.LEFT);
				query.distinct(true);
				return cb.like(feats.get("name"), "Врожденное колдовство%");
			});
		}
		List<Book> filterBooks = input.getSearchPanes().getOrDefault("book", Collections.emptySet())
				.stream()
				.map(Book::new)
				.collect(Collectors.toList());
		if (!filterBooks.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("book").in(filterBooks));
		}
		input.getSearchPanes().clear();
		
		DataTablesOutput<CreatureDto> output = repo.findAll(input, null, specification, creature -> new CreatureDto(creature));
		
		Map<String, List<Item>> options = new HashMap<>();

		repo.countTotalCreatureByCr().stream().map(this::convertCr).forEach(v -> addItem("exp", options, v));

		repo.countTotalCreatureByType().stream() .map(c -> new
				  Item(String.valueOf(c.getField().getCyrilicName()),
				  String.valueOf(c.getField()), c.getTotal(), c.getTotal())) .forEach(v -> addItem("type", options, v));
		repo.countTotalCreatureBySize().stream() .map(c -> new
				  Item(String.valueOf(c.getField().getCyrilicName()),
				  c.getField().name(), c.getTotal(), c.getTotal())) .forEach(v -> addItem("size", options, v));

		addItem("legendary", options, new Item("Колдовство", "Колдовство", 0L, 0L));
		addItem("legendary", options, new Item("Врожденное колдовство", "Врожденное колдовство", 0L, 0L));
		addItem("legendary", options, new Item("Легендарные действия", "Легендарные действия", 0L, 0L));
		addItem("legendary", options, new Item("Логово", "Логово", 0L, 0L));

		repo.countTotalCreatureByBook(settings == null ? EnumSet.of(TypeBook.OFFICAL) : settings.getTypeBooks()).stream() .map(c -> new
				  Item(String.valueOf(c.getField().getSource()),
				  c.getField().getSource(), c.getTotal(), c.getTotal())) .forEach(v -> addItem("book", options, v));

		SearchPanes sPanes = new SearchPanes(options);
		output.setSearchPanes(sPanes);
		return output;
	}
	
	@GetMapping("/creatures/{id}")
	public FCreature getCreature(@PathVariable Integer id)
	{
		Creature creature = repo.findById(id).get();
		FCreature fcreature = new FCreature(creature);
		return fcreature;
	}

	private String parseCr(String cr) {
		switch (cr) {
		case "0.5": return "1/2";
		case "0.25": return "1/4";
		case "0.125": return "1/8";
		default: return cr;
		}
	}

	private Item convertCr(GroupByCount<String> group) {
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
		DecimalFormat format = new DecimalFormat("0.###");
		format.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ENGLISH));
		return new Item(format.format(cr), group.getField(), group.getTotal(), group.getTotal());
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