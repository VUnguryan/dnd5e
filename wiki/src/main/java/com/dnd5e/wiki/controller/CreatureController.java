package com.dnd5e.wiki.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.model.creature.Action;
import com.dnd5e.wiki.model.creature.ActionType;
import com.dnd5e.wiki.model.creature.Creature;
import com.dnd5e.wiki.model.creature.CreatureRace;
import com.dnd5e.wiki.model.creature.CreatureSize;
import com.dnd5e.wiki.model.creature.CreatureType;
import com.dnd5e.wiki.repository.CreatureRaceRepository;
import com.dnd5e.wiki.repository.CreatureRepository;

@Controller
@RequestMapping({ "/creatures" })
@Scope("session")
public class CreatureController {
	@Autowired
	private CreatureRepository repository;
	@Autowired
	private CreatureRaceRepository creatureRaceRepository;

	private Optional<String> search = Optional.empty();
	private Optional<String> crMin = Optional.empty();
	private Optional<String> crMax = Optional.empty();
	private Optional<CreatureType> typeSelected = Optional.empty();
	private Optional<CreatureSize> sizeSelected = Optional.empty();

	@GetMapping
	public String getCreatures(Model model, @PageableDefault(size = 12, sort = "name") Pageable page) {
		Specification<Creature> specification = null;
		if (search.isPresent()) {
			specification = byName();
		}
		if (typeSelected.isPresent()) {
			specification = (specification == null) ? byType() : Specification.where(specification).and(byType());
		}
		if (crMin.isPresent()) {
			specification = (specification == null) ? byMinExp() : Specification.where(specification).and(byMinExp());
		}
		if (crMax.isPresent()) {
			specification = (specification == null) ? byMaxExp() : Specification.where(specification).and(byMaxExp());
		}
		if (sizeSelected.isPresent()) {
			specification = (specification == null) ? bySize() : Specification.where(specification).and(bySize());
		}
		model.addAttribute("creatures", repository.findAll(specification, page));
		model.addAttribute("filtered",
				crMin.isPresent() 
				|| crMax.isPresent() 
				|| search.isPresent() 
				|| typeSelected.isPresent() 
				|| sizeSelected.isPresent());
		model.addAttribute("searchText", search);
		model.addAttribute("crMin", crMin);
		model.addAttribute("crMax", crMax);
		model.addAttribute("types", CreatureType.values());
		model.addAttribute("sizes", CreatureSize.values());
		model.addAttribute("typeSelected", typeSelected);
		model.addAttribute("sizeSelected", sizeSelected);
		return "creatures";
	}

	@GetMapping("/creature/{id}")
	public String getCreaturView(Model model, @PathVariable Integer id) {
		Creature creature = repository.findById(id).get();
		model.addAttribute("creature", creature);
		List<Action> actions = creature.getActions().stream().filter(a -> a.getActionType() == ActionType.ACTION)
				.collect(Collectors.toList());
		model.addAttribute("actions", actions);
		List<Action> reactions = creature.getActions().stream().filter(a -> a.getActionType() == ActionType.REACTION)
				.collect(Collectors.toList());
		model.addAttribute("reactions", reactions);
		List<Action> legendary = creature.getActions().stream().filter(a -> a.getActionType() == ActionType.LEGENDARY)
				.collect(Collectors.toList());
		model.addAttribute("legendary", legendary);
		return "creatureView";
	}

	@GetMapping("/creature/classic/{id}")
	public String getClassicCreature(Model model, @PathVariable Integer id) {
		Creature creature = repository.findById(id).get();
		model.addAttribute("creature", creature);
		List<Action> actions = creature.getActions().stream().filter(a -> a.getActionType() == ActionType.ACTION)
				.collect(Collectors.toList());
		model.addAttribute("actions", actions);
		List<Action> reactions = creature.getActions().stream().filter(a -> a.getActionType() == ActionType.REACTION)
				.collect(Collectors.toList());
		model.addAttribute("reactions", reactions);
		List<Action> legendary = creature.getActions().stream().filter(a -> a.getActionType() == ActionType.LEGENDARY)
				.collect(Collectors.toList());
		model.addAttribute("legendary", legendary);
		return "classicCreatureView";
	}

	@GetMapping("/race/{id}")
	public String getCreatureRace(Model model, @PathVariable Integer id) {
		CreatureRace race = creatureRaceRepository.getOne(id);
		model.addAttribute("race", race);
		model.addAttribute("creatures", repository.findAllByRaceIdOrderByExpAsc(id));
		return "classesCreature";
	}

	@GetMapping(params = "search")
	public String searchCreature(Model model, String search) {
		this.search = search.isEmpty() ? this.search = Optional.empty() : Optional.of(search);
		return "redirect:/creatures";
	}

	@GetMapping(params = { "sort", "type", "crMin", "crMax", "cSize" })
	public String filter(Model model, String sort, String type, String crMin, String crMax, String cSize) {
		this.typeSelected = "ALL".equals(type) ? Optional.empty() : Optional.of(CreatureType.valueOf(type));
		this.crMin = "-1".equals(crMin) ? Optional.empty() : Optional.of(crMin);
		this.crMax = "-1".equals(crMax) ? Optional.empty() : Optional.of(crMax);
		this.sizeSelected = "ALL".equals(cSize) ? Optional.empty() : Optional.of(CreatureSize.valueOf(cSize));
		return "redirect:/creatures?sort=" + sort;
	}
	
	@GetMapping(params = { "clear" })
	public String cleaarFilters() {
		this.search = Optional.empty();
		this.crMin = Optional.empty();
		this.crMax = Optional.empty();
		this.sizeSelected = Optional.empty();
		this.typeSelected = Optional.empty();
		return "redirect:/creatures";
	}
	
	private Specification<Creature> byName() {
		return (root, query, cb) -> cb.or(cb.like(root.get("name"), "%" + search.get() + "%"),
				cb.like(root.get("englishName"), "%" + search.get() + "%"));
	}

	private Specification<Creature> byType() {
		return (root, query, cb) -> cb.and(cb.equal(root.get("type"), typeSelected.get()));
	}

	private Specification<Creature> byMinExp() {
		return (root, query, cb) -> cb.and(cb.greaterThanOrEqualTo(root.get("exp"), toExp(crMin.get())));
	}

	private Specification<Creature> byMaxExp() {
		return (root, query, cb) -> cb.and(cb.lessThanOrEqualTo(root.get("exp"), toExp(crMax.get())));
	}

	private Specification<Creature> bySize() {
		return (root, query, cb) -> cb.and(cb.equal(root.get("size"), sizeSelected.get()));
	}

	private int toExp(String cr) {
		switch (cr) {
		case "0":
			return 10;
		case "1/8":
			return 25;
		case "1/4":
			return 50;
		case "1/2":
			return 100;
		case "1":
			return 200;
		case "2":
			return 450;
		case "3":
			return 700;
		case "4":
			return 1100;
		case "5":
			return 1800;
		case "6":
			return 2300;
		case "7":
			return 2900;
		case "8":
			return 3900;
		case "9":
			return 5000;
		case "10":
			return 5900;
		case "11":
			return 7200;
		case "12":
			return 8400;
		case "13":
			return 10000;
		case "14":
			return 11500;
		case "15":
			return 13000;
		case "16":
			return 15000;
		case "17":
			return 18000;
		case "18":
			return 20000;
		case "19":
			return 22000;
		case "20":
			return 25000;
		case "21":
			return 25000;
		case "22":
			return 41000;
		case "23":
			return 50000;
		case "24":
			return 62000;
		case "25":
			return 75000;
		case "26":
			return 90000;
		case "27":
			return 105000;
		case "28":
			return 120000;
		case "29":
			return 135000;
		case "30":
			return 155000;
		}
		return 0;
	}
}