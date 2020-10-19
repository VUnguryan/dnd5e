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
import com.dnd5e.wiki.dto.SpellDto;
import com.dnd5e.wiki.dto.user.Setting;
import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.creature.DamageType;
import com.dnd5e.wiki.model.hero.classes.HeroClass;
import com.dnd5e.wiki.model.spell.MagicSchool;
import com.dnd5e.wiki.model.spell.Spell;
import com.dnd5e.wiki.repository.datatable.SpellDatatableRepository;

@RestController
public class SpellRestController {
	@Autowired
	private HttpSession session;
	
	@Autowired
	private SpellDatatableRepository repo;
	
	@GetMapping("/spells")
	public DataTablesOutput<SpellDto> getData(@Valid DataTablesInput input, @RequestParam Map<String, String> searchPanes) {
		Setting setting = (Setting) session.getAttribute(SettingRestController.HOME_RULE);
		List<Integer> filterLevels = new ArrayList<>();
		for (int j = 0; j <= 9; j++) {
			String level = searchPanes.get("searchPanes.level." + j);
			if (level != null) {
				filterLevels.add(Integer.valueOf(level));
			}
		}
		List<MagicSchool> filterSchool = new ArrayList<>();
		for (int j = 0; j < MagicSchool.values().length; j++) {
			String school = searchPanes.get("searchPanes.school." + j);
			if (school != null) {
				filterSchool.add(MagicSchool.getMagicSchool(school));
			}
		}
		List<String> filterClasses = new ArrayList<>();
		for (int j = 0; j <= 12; j++) {
			String heroClassName = searchPanes.get("searchPanes.heroClass." + j);
			if (heroClassName != null) {
				filterClasses.add(heroClassName);
			}
		}
		List<DamageType> filterDamageTypes = new ArrayList<>();
		for (int j = 0; j <= DamageType.values().length; j++) {
			String type = searchPanes.get("searchPanes.damageType." + j);
			if (type != null) {
				filterDamageTypes.add(DamageType.parse(type));
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
		DataTablesOutput<SpellDto> output;
		Specification<Spell> specification = null;
		if (setting == null || !setting.isHomeRule())
		{
			specification = byOfficial();
		}
		if (!filterLevels.isEmpty()) {
			specification = addSpecification(specification,  (root, query, cb) -> root.get("level").in(filterLevels));
		}
		if (!filterSchool.isEmpty()) {
			specification = addSpecification(specification,(root, query, cb) -> root.get("school").in(filterSchool));
		}
		if (!filterClasses.isEmpty())
		{
			specification = addSpecification(specification, (root, query, cb) -> {
				Join<HeroClass, Spell> hero = root.join("heroClass", JoinType.LEFT);
				return cb.and(hero.get("name").in(filterClasses));
			});
		}
		if (!filterDamageTypes.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> {
				Join<DamageType, Spell> damageType = root.join("damageType", JoinType.LEFT);
				return damageType.in(filterDamageTypes);
			});
		}
		if (!filterBooks.isEmpty()) {
			specification = addSpecification(specification, (root, query, cb) -> root.get("book").in(filterBooks));
		}
		output = repo.findAll(input, null, specification, SpellDto::new);
		
		SearchPanes sPanes = new SearchPanes();
		Map<String, List<Item>> options = new HashMap<>();

		repo.countTotalSpellByLevel().stream()
			.map(c -> new Item(String.valueOf(c.getField()), c.getTotal(), String.valueOf(c.getField()), c.getTotal()))
			.forEach(v -> addItem("level", options, v));

		repo.countTotalSpellBySchool().stream()
			.map(c -> new Item(c.getField().getName(), c.getTotal(), c.getField().name(), c.getTotal()))
			.forEach(v -> addItem("school", options, v));
		
		repo.countTotalSpellByHeroClass().stream()
			.map(c -> new Item(c.getField().getName(), c.getTotal(), String.valueOf(c.getField().getId()), c.getTotal()))
			.forEach(v -> addItem("heroClass", options, v));
		
		repo.countTotalSpellByBook().stream()
			.map(c -> new Item(c.getField().getSource(), c.getTotal(), String.valueOf(c.getField().getSource()), c.getTotal()))
			.forEach(v -> addItem("book", options, v));

		repo.countTotalSpellByTypeDamage().stream()
			.map(c -> new Item(c.getField().getCyrilicName(), c.getTotal(), String.valueOf(c.getField().name()), c.getTotal()))
			.forEach(v -> addItem("damageType", options, v));
		
		sPanes.setOptions(options); 
		SearchPanesOutput<SpellDto> spOutput = new SearchPanesOutput<>(output);
		spOutput.setSearchPanes(sPanes);
		return spOutput;
	}
	
	private Specification<Spell> addSpecification(Specification<Spell> specification , Specification<Spell> addSpecification){
		if (specification == null) {
			return Specification.where(addSpecification);
		}
		return specification.and(addSpecification);
	}
	
	private void addItem(String key, Map<String, List<Item>> options, Item v) {
		options.computeIfAbsent(key, s -> new ArrayList<>()).add(v);
	}
	
	private Specification<Spell> byOfficial() {
		return (root, query, cb) -> {
			Join<Book, Spell> hero = root.join("book", JoinType.LEFT);
			return cb.equal(hero.get("type"), TypeBook.OFFICAL);
		};	
	}
}