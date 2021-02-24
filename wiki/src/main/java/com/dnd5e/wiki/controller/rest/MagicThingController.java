package com.dnd5e.wiki.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
public class MagicThingController {
	@Autowired
	private HttpSession session;
	
	@Autowired
	private ArtifactRepository repo;
	
	@GetMapping("/magicThings")
	public DataTablesOutput<MagicThingDto> getData(@Valid DataTablesInput input, @RequestParam Map<String, String> searchPanes) {
		Setting settings = (Setting) session.getAttribute(SettingRestController.SETTINGS);
		Set<TypeBook> sources = SourceUtil.getSources(settings);

		DataTablesOutput<MagicThingDto> output;
		Specification<MagicThing> specification = bySources(sources);
		List<Rarity> filterRarities = new ArrayList<>();
		for (int j = 0; j <= Rarity.values().length; j++) {
			String rarity = searchPanes.get("searchPanes.rarity." + j);
			if (rarity != null) {
				filterRarities.add(Rarity.parse(rarity));
			}
		}
		if (!filterRarities.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("rarity").in(filterRarities));
		}
		List<MagicThingType> filterTypes = new ArrayList<>();
		for (int j = 0; j <= MagicThingType.values().length; j++) {
			String type = searchPanes.get("searchPanes.type." + j);
			if (type != null) {
				filterTypes.add(MagicThingType.parse(type));
			}
		}
		if (!filterTypes.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("type").in(filterTypes));
		}
		
		
		String custTrue = searchPanes.get("searchPanes.customization.0");
		if (custTrue!= null && custTrue.equals("Есть")) {
			specification = addSpecification(specification, (root, query, cb) -> cb.and(cb.equal(root.get("customization"), 1)));
		} else if (custTrue!= null && custTrue.equals("Нет")) {
			specification = addSpecification(specification, (root, query, cb) -> cb.and(cb.equal(root.get("customization"), 0)));
		}
		
		List<Book> filterBooks = new ArrayList<>();
		for (int j = 0; j <= 21; j++) {
			String type = searchPanes.get("searchPanes.book." + j);
			if (type != null) {
				Book book = new Book();
				book.setSource(type);
				filterBooks.add(book);
			}
		}
		if (!filterBooks.isEmpty()) {
			specification = addSpecification(specification,  (root, query, cb) -> root.get("book").in(filterBooks));
		}
		output = repo.findAll(input, null, specification, MagicThingDto::new);
		
		SearchPanes sPanes = new SearchPanes();
		Map<String, List<Item>> options = new HashMap<>();
	
		repo.countTotalMagicThingsByRarity().stream()
			.map(c -> new Item<String>(c.getField().getCyrilicName(), c.getTotal(), String.valueOf(c.getField()), c.getTotal()))
			.forEach(v -> addItem("rarity", options, v));

		repo.countTotalMagicThingsByType().stream()
			.map(c -> new Item<String>(c.getField().getCyrilicName(), c.getTotal(), String.valueOf(c.getField()), c.getTotal()))
			.forEach(v -> addItem("type", options, v));
		
		repo.countTotalMagicThingsByBook().stream()
			.map(c -> new Item<String>(c.getField().getSource(), c.getTotal(), String.valueOf(c.getField()), c.getTotal()))
			.forEach(v -> addItem("book", options, v));
		
		repo.countTotalMagicThingsByCustomization().stream()
			.map(c -> new Item<String>(c.getField() ? "Есть" : "Нет", c.getTotal(), c.getField() ? "Есть" : "Нет", c.getTotal()))
			.forEach(v -> addItem("customization", options, v));
		
		sPanes.setOptions(options); 
		SearchPanesOutput<MagicThingDto> spOutput = new SearchPanesOutput<>(output);
		spOutput.setSearchPanes(sPanes);
		return spOutput;
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