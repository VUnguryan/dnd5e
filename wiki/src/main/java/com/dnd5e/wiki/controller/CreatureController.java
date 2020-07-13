package com.dnd5e.wiki.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.model.creature.Action;
import com.dnd5e.wiki.model.creature.ActionType;
import com.dnd5e.wiki.model.creature.Creature;
import com.dnd5e.wiki.model.creature.CreatureRace;
import com.dnd5e.wiki.repository.CreatureRaceRepository;
import com.dnd5e.wiki.repository.CreatureRepository;

@Controller
@RequestMapping({ "/entities" })
public class CreatureController {
	@Autowired
	private CreatureRepository creatureRepo;
	
	@Autowired
	private CreatureRaceRepository creatureRaceRepo;
	
	@GetMapping("/creatures")
	public String getCreatures() {
		return "datatable/creatures";
	}

	@GetMapping("/creature/{id}")
	public String getCreaturView(Model model, @PathVariable Integer id) {
		Creature creature = creatureRepo.findById(id).get();
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
		Creature creature = creatureRepo.findById(id).get();
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
		CreatureRace race = creatureRaceRepo.getOne(id);
		model.addAttribute("creatureRaces", creatureRaceRepo.findAll(Sort.by("name")));
		model.addAttribute("race", race);
		model.addAttribute("creatures", creatureRepo.findAllByRaceIdOrderByExpAsc(id));
		return "classesCreature";
	}

	/*
	 * private Specification<Creature> byName() { return (root, query, cb) ->
	 * cb.or(cb.like(root.get("name"), "%" + search + "%"),
	 * cb.like(root.get("englishName"), "%" + search + "%")); }
	 * 
	 * private Specification<Creature> byType() { return (root, query, cb) ->
	 * cb.and(cb.equal(root.get("type"), typeSelected.get())); }
	 * 
	 * private Specification<Creature> byMinExp() { return (root, query, cb) ->
	 * cb.and(cb.greaterThanOrEqualTo(root.get("exp"), toExp(crMin.get()))); }
	 * 
	 * private Specification<Creature> byMaxExp() { return (root, query, cb) ->
	 * cb.and(cb.lessThanOrEqualTo(root.get("exp"), toExp(crMax.get()))); }
	 * 
	 * private Specification<Creature> bySize() { return (root, query, cb) ->
	 * cb.and(cb.equal(root.get("size"), sizeSelected.get())); }
	 * 
	 * private Specification<Creature> byHabitatType() { return (root, query, cb) ->
	 * { Join<HabitatType, Creature> hero = root.join("habitates", JoinType.LEFT);
	 * return hero.in(habitatsSelected); }; }
	 * 
	 * private Specification<Creature> byVulnerability(){ return (root, query, cb)
	 * -> { Join<DamageType, Creature> join = root.join("vulnerabilityDamages");
	 * return join.in(vulnerabilitySelected); }; }
	 * 
	 * private Specification<Creature> byResistance(){ return (root, query, cb) -> {
	 * Join<DamageType, Creature> join = root.join("resistanceDamages"); return
	 * join.in(resistanceSelected); }; }
	 * 
	 * private Specification<Creature> byImmunity(){ return (root, query, cb) -> {
	 * Join<DamageType, Creature> join = root.join("immunityDamages"); return
	 * join.in(immunitySelected); }; }
	 * 
	 * private Specification<Creature> byStateImmunity(){ return (root, query, cb)
	 * -> { Join<State, Creature> join = root.join("immunityStates"); return
	 * join.in(stateImmunitySelected); }; }

	private Specification<Creature> byAction(){
		return (root, query, cb) -> {
			Join<Action, Creature> join = root.join("actions");
			query.distinct(true);
			return cb.equal(join.get("actionType"), actionTypeSelected);
		};
	}
	
	private Specification<Creature> byCaster(){
		return (root, query, cb) -> {
			Join<CreatureTrait, Creature> join = root.join("feats");
			query.distinct(true);
			return cb.like(join.get("name"), "%"+casterSelected+"%");
		};
	}

	private Specification<Creature> byOfficial() {
		return (root, query, cb) -> {
			Join<Book, Creature> hero = root.join("book", JoinType.LEFT);
			return cb.equal(hero.get("type"), TypeBook.OFFICAL);
		};	
	}

	private Specification<Creature> bySource() {
		return (root, query, cb) -> {
			Join<Book, Creature> book = root.join("book", JoinType.LEFT);
			return cb.and(book.get("source").in(selectedSources));
		};	
	}
	 */	
	private static int toExp(String cr) {
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