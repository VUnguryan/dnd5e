package com.dnd5e.wiki.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
import com.dnd5e.wiki.dto.EquipmentDto;
import com.dnd5e.wiki.dto.TraitDto;
import com.dnd5e.wiki.dto.user.Setting;
import com.dnd5e.wiki.model.AbilityType;
import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.creature.SkillType;
import com.dnd5e.wiki.model.hero.Trait;
import com.dnd5e.wiki.model.spell.Spell;
import com.dnd5e.wiki.model.stock.Equipment;
import com.dnd5e.wiki.model.stock.EquipmentType;
import com.dnd5e.wiki.repository.EquipmentRepository;
import com.dnd5e.wiki.repository.datatable.TraitRepository;

@RestController
public class EquipmentsRestController {
	@Autowired
	private HttpSession session;

	@Autowired
	private EquipmentRepository repo;

	@GetMapping("/equipments")
	public SearchPanesOutput<EquipmentDto> getData(@Valid DataTablesInput input,
			@RequestParam Map<String, String> searchPanes) {

		List<EquipmentType> filterTypes = new ArrayList<>();
		for (int j = 0; j <= EquipmentType.values().length; j++) {
			String abylity = searchPanes.get("searchPanes.type." + j);
			if (abylity != null) {
				filterTypes.add(EquipmentType.parse(abylity));
			}
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
		Specification<Equipment> specification = null;
		Setting setting = (Setting) session.getAttribute(SettingRestController.HOME_RULE);
		if (setting == null || !setting.isHomeRule())
		{
			specification = byOfficial();
		}
		if (!filterTypes.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("type").in(filterTypes));
		}
		if (!filterBooks.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("book").in(filterBooks));
		}

		DataTablesOutput<EquipmentDto> output = repo.findAll(input, specification, specification,
				i -> new EquipmentDto(i));

		SearchPanes sPanes = new SearchPanes();
		Map<String, List<Item>> options = new HashMap<>();

		repo.countTotalEquipmentByType().stream().filter(o -> o.getField() != null).map(
				c -> new Item(c.getField().getCyrilicName(), c.getTotal(), String.valueOf(c.getField()), c.getTotal()))
				.forEach(v -> addItem("type", options, v));

		repo.countTotalEquipmentByBook().stream()
				.map(c -> new Item(c.getField().getSource(), c.getTotal(), String.valueOf(c.getField()), c.getTotal()))
				.forEach(v -> addItem("book", options, v));

		sPanes.setOptions(options);
		SearchPanesOutput<EquipmentDto> spOutput = new SearchPanesOutput<>(output);
		spOutput.setSearchPanes(sPanes);
		return spOutput;
	}

	private <T> Specification<T> addSpecification(Specification<T> specification, Specification<T> addSpecification) {
		if (specification == null) {
			return Specification.where(addSpecification);
		}
		return specification.and(addSpecification);
	}
	
	private Specification<Equipment> byOfficial() {
		return (root, query, cb) -> {
			Join<Book,Equipment> hero = root.join("book", JoinType.LEFT);
			return cb.equal(hero.get("type"), TypeBook.OFFICAL);
		};	
	}
	private void addItem(String key, Map<String, List<Item>> options, Item v) {
		options.computeIfAbsent(key, s -> new ArrayList<>()).add(v);
	}
}