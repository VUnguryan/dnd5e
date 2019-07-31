package com.dnd5e.wiki.controller.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd5e.wiki.controller.rest.model.json.AC;
import com.dnd5e.wiki.controller.rest.model.json.Abilities;
import com.dnd5e.wiki.controller.rest.model.json.Action;
import com.dnd5e.wiki.controller.rest.model.json.HP;
import com.dnd5e.wiki.controller.rest.model.json.JsonCreature;
import com.dnd5e.wiki.controller.rest.model.json.LegendaryAction;
import com.dnd5e.wiki.controller.rest.model.json.Reaction;
import com.dnd5e.wiki.controller.rest.model.json.Safe;
import com.dnd5e.wiki.controller.rest.model.json.Skill;
import com.dnd5e.wiki.controller.rest.model.json.TraitJS;
import com.dnd5e.wiki.controller.rest.model.xml.Conpendium;
import com.dnd5e.wiki.model.creature.ActionType;
import com.dnd5e.wiki.model.creature.Creature;
import com.dnd5e.wiki.model.creature.CreatureTrait;
import com.dnd5e.wiki.model.creature.DamageType;
import com.dnd5e.wiki.model.creature.SavingThrow;
import com.dnd5e.wiki.model.creature.State;
import com.dnd5e.wiki.repository.ArtifactRepository;
import com.dnd5e.wiki.repository.CreatureRepository;
import com.dnd5e.wiki.repository.EquipmentRepository;
import com.dnd5e.wiki.repository.SpellRepository;

@RestController
@RequestMapping("/admin/export")
public class ExportController {
	private static final String HTML_REGEXP = "\\\\<[^\\>]*\\>";
	
	@Autowired
	private CreatureRepository creatureRepo;
	
	@Autowired
	private SpellRepository spellRepo;
	
	@Autowired
	private ArtifactRepository artRepo;
	
	@Autowired
	private EquipmentRepository equRepo;
	
	@GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<JsonCreature> getJsonCreatures() {
		List<JsonCreature> creatures = new ArrayList<>();
		for (Creature creature : creatureRepo.findAll()) {
			JsonCreature jsonCreature = new JsonCreature();

			AC ac = new AC();
			ac.withValue(String.valueOf(creature.getAC()));
			jsonCreature.withAC(ac);

			Abilities abilities = new Abilities();
			abilities.withCha(String.valueOf(creature.getCharisma()));
			abilities.withCon(String.valueOf(creature.getConstitution()));
			abilities.withDex(String.valueOf(creature.getDexterity()));
			abilities.withInt(String.valueOf(creature.getIntellect()));
			abilities.withStr(String.valueOf(creature.getStrength()));
			abilities.withWis(String.valueOf(creature.getWizdom()));
			jsonCreature.withAbilities(abilities);

			List<Action> jsonActions = new ArrayList<>();
			List<com.dnd5e.wiki.model.creature.Action> actions = creature.getActions();
			for (com.dnd5e.wiki.model.creature.Action action : actions.stream()
					.filter(a -> a.getActionType() == ActionType.ACTION).collect(Collectors.toList())) {
				Action jsonAction = new Action();
				if (action.getDescription() != null) {
					jsonAction.withName(action.getName())
							.withContent(action.getDescription().replaceAll(HTML_REGEXP, ""));
				}
				jsonActions.add(jsonAction);
			}
			jsonCreature.withActions(jsonActions);

			jsonCreature.withChallenge(creature.getChallengeRating());

			jsonCreature.withConditionImmunities(
					creature.getImmunityStates().stream().map(State::getCyrilicName).collect(Collectors.toList()));
			jsonCreature.withDamageImmunities(creature.getImmunityDamages().stream().map(DamageType::getCyrilicName)
					.collect(Collectors.toList()));
			jsonCreature.withDamageResistances(creature.getResistanceDamages().stream().map(DamageType::getCyrilicName)
					.collect(Collectors.toList()));
			jsonCreature.withDamageVulnerabilities(creature.getVulnerabilityDamages().stream()
					.map(DamageType::getCyrilicName).collect(Collectors.toList()));
			if (creature.getDescription() != null) {
				jsonCreature.withDescription(creature.getDescription().replaceAll(HTML_REGEXP, ""));
			}
			String hp;
			if (creature.getBonusHP() == null) {
				hp = String.format("%d%s", creature.getCountDiceHp(), creature.getDiceHp().name().toLowerCase());
			} else {
				hp = String.format("%d%s+%d", creature.getCountDiceHp(), creature.getDiceHp().name().toLowerCase(),
						creature.getBonusHP());
			}
			jsonCreature.withHP(new HP().withValue(String.valueOf(creature.getAverageHp())).withNotes(hp));

			String init = String.valueOf(creature.getDexterity() - 10 < 10 ? (creature.getDexterity() - 11) / 2
					: (creature.getDexterity() - 10) / 2);
			jsonCreature.withInitiativeModifier(init);

			List<LegendaryAction> jsonLegendaryAction = new ArrayList<LegendaryAction>();
			for (com.dnd5e.wiki.model.creature.Action action : actions.stream()
					.filter(a -> a.getActionType() == ActionType.LEGENDARY).collect(Collectors.toList())) {
				LegendaryAction jsonReaction = new LegendaryAction();
				jsonReaction.withName(action.getName())
						.withContent(action.getDescription().replaceAll(HTML_REGEXP, ""));
				jsonLegendaryAction.add(jsonReaction);
			}
			jsonCreature.withLegendaryActions(jsonLegendaryAction);

			jsonCreature.withName(creature.getName());
			List<Reaction> jsonReactions = new ArrayList<>();
			for (com.dnd5e.wiki.model.creature.Action action : actions.stream()
					.filter(a -> a.getActionType() == ActionType.REACTION).collect(Collectors.toList())) {
				Reaction jsonReaction = new Reaction();
				jsonReaction.withName(action.getName())
						.withContent(action.getDescription().replaceAll(HTML_REGEXP, ""));
				jsonReactions.add(jsonReaction);
			}
			jsonCreature.withReactions(jsonReactions);

			List<Safe> saves = new ArrayList<>();
			for (SavingThrow st : creature.getSavingThrows()) {
				Safe safe = new Safe();
				safe.withName(firstLetter(st.getAbility().name().toLowerCase().substring(0,3))).withModifier(String.valueOf(st.getBonus()));
				saves.add(safe);
			}
			jsonCreature.withSaves(saves);
			List<Skill> skills = new ArrayList<>();
			for (com.dnd5e.wiki.model.creature.Skill sk : creature.getSkills()) {
				Skill skill = new Skill();
				if (sk != null && sk.getType() != null) {
					skill.withModifier(String.valueOf(sk.getBonus())).withName(firstLetter(sk.getType().name().toLowerCase()));
				}
				skills.add(skill);
			}
			jsonCreature.withSkills(skills);

			List<String> speeds = new ArrayList<>();
			speeds.add(creature.getSpeed() + " фт.");
			if (creature.getFlySpeed() != null) {
				speeds.add("летая " + creature.getFlySpeed() + " фт.");
			}
			if (creature.getSwimmingSpped() != null) {
				speeds.add("плавая " + creature.getSwimmingSpped() + " фт.");
			}
			if (creature.getDiggingSpeed() != null) {
				speeds.add("копая " + creature.getDiggingSpeed() + " фт.");
			}
			if (creature.getClimbingSpeed() != null) {
				speeds.add("лазая " + creature.getClimbingSpeed() + " фт.");
			}
			jsonCreature.withSpeed(speeds);

			List<TraitJS> traits = new ArrayList<>();
			for (CreatureTrait feat : creature.getFeats()) {
				TraitJS trait = new TraitJS();
				trait.withName(feat.getName()).withContent(feat.getDescription().replaceAll(HTML_REGEXP, ""));
				traits.add(trait);
			}
			jsonCreature.withTraits(traits);
			String size = firstLetter(creature.getSize().name().toLowerCase());
			jsonCreature
					.withType(size + " " + creature.getType().name().toLowerCase()+ ", " + creature.getAlignment().getCyrilicName());
			jsonCreature.withId(String.valueOf(creature.getId()));
			creatures.add(jsonCreature);
		}
		return creatures;
	}
	
	@GetMapping(value = "/xml" , produces = MediaType.APPLICATION_XML_VALUE)
	public Conpendium getXmlCreatures() {
		Conpendium list = new Conpendium();
		list.setMonsters(creatureRepo.findAll());
		list.setSpells(spellRepo.findAll());
		list.setMagicItems(artRepo.findAll());
		list.setItems(equRepo.findAll());
		return list;
	}
	
	private String firstLetter(String string) {
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}
}
