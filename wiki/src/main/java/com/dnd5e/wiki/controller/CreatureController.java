package com.dnd5e.wiki.controller;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.model.creature.Action;
import com.dnd5e.wiki.model.creature.ActionType;
import com.dnd5e.wiki.model.creature.Creature;
import com.dnd5e.wiki.model.creature.CreatureRace;
import com.dnd5e.wiki.model.hero.Condition;
import com.dnd5e.wiki.repository.ConditionRepository;
import com.dnd5e.wiki.repository.CreatureRaceRepository;
import com.dnd5e.wiki.repository.CreatureRepository;

@Controller
@RequestMapping({ "/entities" })
public class CreatureController {
	@Autowired
	private CreatureRepository creatureRepo;
	
	@Autowired
	private CreatureRaceRepository creatureRaceRepo;
	
	@Autowired
	private ConditionRepository conditionRepo;
	
	@GetMapping("/creatures")
	public String getCreatures(Device device) {
		if (device.isMobile())
		{
			return "datatable/creatures";
		}
		return "datatable/creatures2";
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

		Map<Integer, Condition> conditions = conditionRepo.findAll().stream().collect(Collectors.toMap(Condition::getId, Function.identity(), (o1, o2) -> o1));
		model.addAttribute("conditions", conditions);
		return "creatureView";
	}

	@GetMapping("/race/{id}")
	public String getCreatureRace(Model model, @PathVariable Integer id) {
		CreatureRace race = creatureRaceRepo.getOne(id);
		model.addAttribute("creatureRaces", creatureRaceRepo.findAll(Sort.by("name")));
		model.addAttribute("race", race);
		model.addAttribute("creatures", creatureRepo.findAllByRaceIdOrderByExpAsc(id));
		return "classesCreature";
	}
}