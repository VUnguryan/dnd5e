package com.dnd5e.wiki.controller.rest.model.json.shaped;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.creature.ActionType;
import com.dnd5e.wiki.model.creature.ArmorType;
import com.dnd5e.wiki.model.creature.Creature;
import com.dnd5e.wiki.model.creature.DamageType;
import com.dnd5e.wiki.model.creature.Language;
import com.dnd5e.wiki.model.creature.Skill;
import com.dnd5e.wiki.model.creature.Condition;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class SMonster {
	private String name;
	private String size;
	private String type;
	private String alignment;
	@JsonProperty("AC")
	private String ac;
	@JsonProperty("HP")
	private String hp;
	private String speed;
	private Byte strength;
	private Byte dexterity;
	private Byte constitution;
	private Byte intelligence;
	private Byte wisdom;
	private Byte charisma;
	private String savingThrows;
	private String skills;
	private String damageVulnerabilities;
	private String damageResistances;
	private String damageImmunities;
	private String conditionImmunities;
	private String senses = "";
	private String languages;
	private float challenge;
	private Byte legendaryPoints;
	private List<String> environments;
	
	@JsonProperty("traits")
	private List<STrait> traits;
	@JsonProperty("actions")
	private List<SAction> actions;
	@JsonProperty("reactions")
	private List<SAction> reactions;
	
	@JsonProperty("legendaryActions")
	private List<SAction> legendaryActions;
	
	public SMonster(Creature creature) {
		name = creature.getName();
		size = StringUtils.capitalize(creature.getSize().name().toLowerCase());
		type = creature.getType().getCyrilicName();
		alignment = creature.getAlignment().name().toLowerCase().replace('_', ' ').replace("without", "unaligned");
		ac = String.valueOf(creature.getAC());
		if (!creature.getArmorTypes().isEmpty()) {
			ac+= String.format(" (%s)", creature.getArmorTypes().stream().map(ArmorType::getCyrillicName).collect(Collectors.joining(", ")));
		}
		hp = creature.getHp();
		speed = creature.getAllSpeedEnglish();
		strength = creature.getStrength();
		dexterity = creature.getDexterity();
		constitution = creature.getConstitution();
		intelligence = creature.getIntellect();
		wisdom = creature.getWizdom();
		charisma = creature.getCharisma();
		savingThrows = creature.getSavingThrows().isEmpty() ? null : creature.getSavingThrows().stream()
				.map(s-> String.format("%s %+d", StringUtils.capitalize(s.getAbility().name().toLowerCase().substring(0,3)), s.getBonus()))
				.collect(Collectors.joining(", "));
		skills = creature.getSkills().isEmpty() ? null : (creature.getSkills().stream()
				.map(Skill::getText)
				.collect(Collectors.joining(", ")));
		damageVulnerabilities = creature.getVulnerabilityDamages().isEmpty() ? null : creature.getVulnerabilityDamages().stream().map(DamageType::getCyrilicName).collect(Collectors.joining(", "));
		damageResistances = creature.getResistanceDamages().isEmpty() ? null : creature.getResistanceDamages().stream().map(DamageType::getCyrilicName).collect(Collectors.joining(", "));
		damageImmunities = creature.getImmunityDamages().isEmpty() ? null : creature.getImmunityDamages().stream().map(DamageType::getCyrilicName).collect(Collectors.joining(", "));
		conditionImmunities = creature.getImmunityStates().isEmpty() ? null : creature.getImmunityStates().stream().map(Condition::getCyrilicName).collect(Collectors.joining(", "));
		List<String> sensesList = new ArrayList<>();
		if (creature.getDarkvision() != null) {
			sensesList.add(String.format("darkvision  %d ft.", creature.getDarkvision())); 
		}
		if (creature.getVibration() != null) {
			sensesList.add(String.format("tremorsense  %d ft.", creature.getVibration()));
		}
		if (creature.getBlindsight() != null) {
			String sense = String.format("blindsight  %d ft.", creature.getBlindsight());
			if (creature.getBlindsightRadius() != null) {
				sense += " (blind beyond this radius)";
			}
			sensesList.add(sense);
		}
		if (creature.getTrysight() != null) {
			sensesList.add(String.format("truesight  %d ft.", creature.getTrysight()));
		}
		senses = sensesList.isEmpty() ? null : sensesList.stream().collect(Collectors.joining(", "));
		languages = creature.getLanguages().stream().map(Language::getName).collect(Collectors.joining(", "));
		if(creature.getChallengeRating().equals("1/2")) {
			challenge = 1/2f;
		} else if (creature.getChallengeRating().equals("1/4")) {
			challenge = 1/4f;
		}
		else if (creature.getChallengeRating().equals("1/8")) {
			challenge = 1/8f;
		}
		else
		{
			challenge = Float.valueOf(creature.getChallengeRating());	
		}
		traits = creature.getFeats().isEmpty() ? null : creature.getFeats().stream().map(STrait::new).collect(Collectors.toList());
		actions = creature.getActions().isEmpty() ? null : creature.getActions().stream().map(SAction::new).collect(Collectors.toList());
		reactions = creature.getReactions().isEmpty() ? null : creature.getReactions().stream().map(SAction::new).collect(Collectors.toList());
		if (!creature.getLanguages().isEmpty()) {
			legendaryPoints = 3;
			legendaryActions = creature.getLanguages().isEmpty() ? null : creature.getLegendaries().stream().map(SAction::new).collect(Collectors.toList());
		}
	}
}