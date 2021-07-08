package com.dnd5e.wiki.controller.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

import com.dnd5e.wiki.dto.MagicThingDto;
import com.dnd5e.wiki.dto.user.Setting;
import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.treasure.MagicThing;
import com.dnd5e.wiki.model.treasure.MagicThingType;
import com.dnd5e.wiki.model.treasure.Rarity;
import com.dnd5e.wiki.repository.datatable.ArtifactRepository;
import com.dnd5e.wiki.util.SourceUtil;

@RestController
public class MagicThingRestController {
	@Autowired
	private HttpSession session;
	
	@Autowired
	private ArtifactRepository repo;
	
	@GetMapping("/data/magicThings")
	public DataTablesOutput<MagicThingDto> getData(@Valid DataTablesInput input, @RequestParam Map<String, String> queryParameters) {
		Setting settings = (Setting) session.getAttribute(SettingRestController.SETTINGS);
		Set<TypeBook> sources = SourceUtil.getSources(settings);
		Specification<MagicThing> specification = bySources(sources);
		input.parseSearchPanesFromQueryParams(queryParameters, Arrays.asList("rarity", "type", "customization", "book"));

		List<Rarity> filterRarities = input.getSearchPanes().getOrDefault("rarity", Collections.emptySet())
			.stream()
			.map(Rarity::valueOf)
			.collect(Collectors.toList());
		if (!filterRarities.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("rarity").in(filterRarities));
		}
		List<MagicThingType> filterTypes = input.getSearchPanes().getOrDefault("type", Collections.emptySet())
				.stream()
				.map(MagicThingType::valueOf)
				.collect(Collectors.toList());
		if (!filterTypes.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("type").in(filterTypes));
		}
		Set<String> customizatios = input.getSearchPanes().getOrDefault("customization", Collections.emptySet());
		if (customizatios.contains("Есть")) {
			specification = addSpecification(specification, (root, query, cb) -> cb.and(cb.equal(root.get("customization"), 1)));
		} 
		if (customizatios.contains("Нет")) {
			specification = addSpecification(specification, (root, query, cb) -> cb.and(cb.equal(root.get("customization"), 0)));
		}
		Set<String> filterBooks = input.getSearchPanes().getOrDefault("book", Collections.emptySet());
		if (!filterBooks.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("book").get("source").in(filterBooks));
		}
		input.getSearchPanes().clear();
		DataTablesOutput<MagicThingDto> output = repo.findAll(input, specification, specification, MagicThingDto::new);
		
		Map<String, List<Item>> options = new HashMap<>();
	
		repo.countTotalMagicThingsByRarity().stream()
			.map(c -> new Item(c.getField().getCyrilicName(), c.getField().name(), c.getTotal(), c.getTotal()))
			.forEach(v -> addItem("rarity", options, v));

		repo.countTotalMagicThingsByType().stream()
			.map(c -> new Item(c.getField().getCyrilicName(), c.getField().name(), c.getTotal(), c.getTotal()))
			.forEach(v -> addItem("type", options, v));
		
		repo.countTotalMagicThingsByBook(settings == null ? EnumSet.of(TypeBook.OFFICAL) : settings.getTypeBooks()).stream()
			.map(c -> new Item(c.getField().getSource(), c.getField().getSource(), c.getTotal(), c.getTotal()))
			.forEach(v -> addItem("book", options, v));
		
		repo.countTotalMagicThingsByCustomization().stream()
			.map(c -> new Item(c.getField() ? "Есть" : "Нет", c.getField() ? "Есть" : "Нет", c.getTotal(), c.getTotal()))
			.forEach(v -> addItem("customization", options, v));
		
		SearchPanes sPanes = new SearchPanes(options);
		output.setSearchPanes(sPanes);
		return output;
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
	
	private <T> Specification<T> bySources(Set<TypeBook> types) {
		return (root, query, cb) -> root.get("book").get("type").in(types);
	}
}