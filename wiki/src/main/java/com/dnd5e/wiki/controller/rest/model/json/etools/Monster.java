package com.dnd5e.wiki.controller.rest.model.json.etools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.dnd5e.wiki.model.creature.ActionType;
import com.dnd5e.wiki.model.creature.Creature;
import com.dnd5e.wiki.model.creature.DamageType;
import com.dnd5e.wiki.model.creature.Language;
import com.dnd5e.wiki.model.creature.State;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "source", "page", "otherSources", "size", "type", "alignment", "ac", "hp", "speed", "str",
		"dex", "con", "int", "wis", "cha", "skill", "passive", "languages", "cr", "trait", "action", "environment",
		"hasToken", "soundClip", "languageTags", "damageTags", "miscTags", "srd", "save", "senses", "legendary",
		"legendaryGroup", "traitTags", "senseTags", "actionTags", "conditionInflict", "conditionInflictLegendary" })
public class Monster {

	@JsonProperty("name")
	private String name;
	@JsonProperty("source")
	private String source;
	@JsonProperty("page")
	private Integer page;
	@JsonProperty("otherSources")
	private List<OtherSource> otherSources = null;
	
	@JsonProperty("size")
	private String size;
	@JsonProperty("type")
	private Type type;
	@JsonProperty("alignment")
	private List<String> alignment;
	
	@JsonProperty("ac")
	private List<AC> ac;
	@JsonProperty("hp")
	private Hp hp;
	@JsonProperty("speed")
	private Speed speed;
	
	@JsonProperty("str")
	private Byte str;
	@JsonProperty("dex")
	private Byte dex;
	@JsonProperty("con")
	private Byte con;
	@JsonProperty("int")
	private Byte _int;
	@JsonProperty("wis")
	private Byte wis;
	@JsonProperty("cha")
	private Byte cha;
	
	@JsonProperty("skill")
	private Skill skill;
	@JsonProperty("passive")
	private Byte passive;
	@JsonProperty("languages")
	private List<String> languages = null;
	@JsonProperty("cr")
	private String cr;
	
	@JsonProperty("trait")
	private List<Trait> trait = null;

	@JsonProperty("action")
	private List<Action> action = null;
	
	@JsonProperty("reaction")
	private List<Action> reaction = null;

	@JsonProperty("legendary")
	private List<Action> legendary = null;
	
	@JsonProperty("environment")
	private List<String> environment = null;
	
	@JsonProperty("hasToken")
	private Boolean hasToken;
	@JsonProperty("soundClip")
	private SoundClip soundClip;
	@JsonProperty("languageTags")
	private List<String> languageTags = null;
	@JsonProperty("damageTags")
	private List<String> damageTags = null;
	@JsonProperty("miscTags")
	private List<String> miscTags = null;
	@JsonProperty("srd")
	private Boolean srd;
	@JsonProperty("save")
	private Save save;
	@JsonProperty("senses")
	private List<String> senses = null;
	
	@JsonProperty("legendaryGroup")
	private LegendaryGroup legendaryGroup;
	@JsonProperty("traitTags")
	private List<String> traitTags = null;
	@JsonProperty("senseTags")
	private List<String> senseTags = null;
	@JsonProperty("actionTags")
	private List<String> actionTags = null;

	@JsonProperty("vulnerable")
	private List<Object> vulnerable;
	@JsonProperty("resist")
	private List<Object> resist;
	@JsonProperty("immune")
	private List<Object> immune;
	@JsonProperty("conditionImmune")
	private List<String> conditionImmune;
	
	@JsonProperty("conditionInflict")
	private List<String> conditionInflict = null;
	
	@JsonProperty("conditionInflictLegendary")
	private List<String> conditionInflictLegendary = null;

	public Monster(Creature creature) {
		name = creature.getName();
		size = creature.getSize().name().charAt(0) + "";
		type = new Type(creature);
		speed = new Speed(creature);
		alignment = new ArrayList<>();
		switch(creature.getAlignment()) {
			case CHAOTIC_EVIL:
				alignment.add("C");
				alignment.add("E");
				break;
			case CHAOTIC_NEUTRAL:
				alignment.add("C");
				alignment.add("N");
				break;
			case CHAOTIC_GOOD:
				alignment.add("C");
				alignment.add("G");
				break;
			case LAWFUL_EVIL:
				alignment.add("L");
				alignment.add("E");
				break;
			case LAWFUL_GOOD:
				alignment.add("L");
				alignment.add("G");
				break;
			case LAWFUL_NEUTRAL:
				alignment.add("L");
				alignment.add("N");
				break;
			case NEUTRAL:
				alignment.add("N");
				break;
			case NEUTRAL_EVIL:
				alignment.add("N");
				alignment.add("E");
				break;
			case NEUTRAL_GOOD:
				alignment.add("N");
				alignment.add("G");
				break;
				default:
				alignment.add("U");
		}
		hp = new Hp(creature);
		ac = Collections.singletonList(new AC(creature));
		if (creature.getBook().getSource().equals("BESTIARY")) {
			source = "MM";
		}
		if (creature.getBook().getSource().equals("TOA")) {
			source = "ToA";
		}
		if (creature.getBook().getSource().equals("MORDENKAIN")) {
			source = "ToA";
		}
		if (creature.getBook().getSource().equals("VOLO")) {
			source = "VGM";
		}
		if (creature.getBook().getSource().equals("TROT")) {
			source = "RoT";
		}
		if (creature.getBook().getSource().equals("COS")) {
			source = "CoS";
		}
		page = 0;
		str = creature.getStrength();
		dex = creature.getDexterity();
		con = creature.getConstitution();
		_int = creature.getIntellect();
		wis = creature.getWizdom();
		cha = creature.getCharisma();
		if (!creature.getSkills().isEmpty()) {
			skill = new Skill(creature.getSkills());			
		}
		if (!creature.getSavingThrows().isEmpty()) {
			save = new Save(creature.getSavingThrows());
		}
		passive = creature.getPassivePerception();
		senses = new ArrayList<>();
		if (creature.getDarkvision()!= null) {
			senses.add(String.format("тёмное зрение %d фт.", creature.getDarkvision()));
		}
		if (creature.getBlindsight()!= null) {
			senses.add(String.format("слепое зрение %d фт.", creature.getBlindsight(), creature.getBlindsightRadius() == null ? "" : "(слеп за пределами этого радиуса)"));
		}
		if (creature.getTrysight()!= null) {
			senses.add(String.format("истинное зрение %d фт.", creature.getTrysight()));
		}
		if (creature.getTrysight()!= null) {
			senses.add(String.format("чувство вибрации %d фт.", creature.getTrysight()));
		}
		if (senses.isEmpty()) {
			senses = null;
		}
		cr = creature.getChallengeRating();
		languages = creature.getLanguages().stream().map(Language::getName).collect(Collectors.toList());
		if (!creature.getVulnerabilityDamages().isEmpty())
		{
			vulnerable = creature.getVulnerabilityDamages().stream().map(this::toStringDamage).collect(Collectors.toList());
		}
		if (!creature.getResistanceDamages().isEmpty())
		{
			resist = creature.getResistanceDamages().stream().map(this::toStringDamage).collect(Collectors.toList());
		}
		if (!creature.getImmunityDamages().isEmpty())
		{
			immune = creature.getImmunityDamages().stream().map(this::toStringDamage).collect(Collectors.toList());
		}
		if (!creature.getImmunityStates().isEmpty()) {
			conditionImmune = creature.getImmunityStates().stream().map(State::getCyrilicName).collect(Collectors.toList());
		}
		
		if (!creature.getFeats().isEmpty()) {
			trait = creature.getFeats().stream().map(Trait::new).collect(Collectors.toList());			
		}
		action = creature.getActions().stream().filter(a -> a.getActionType() == ActionType.ACTION).map(Action::new).collect(Collectors.toList());
		if (action.isEmpty()) {
			action = null;
		}
		reaction = creature.getActions().stream().filter(a -> a.getActionType() == ActionType.REACTION).map(Action::new).collect(Collectors.toList());
		if (reaction.isEmpty()) {
			reaction = null;
		}
		legendary = creature.getActions().stream().filter(a -> a.getActionType() == ActionType.LEGENDARY).map(Action::new).collect(Collectors.toList());
		if (legendary.isEmpty()) {
			legendary = null;
		}
	}
	
	private Object toStringDamage(DamageType type) {
		String damageType = type.getCyrilicName();
		if (type == DamageType.PHYSICAL_MAGIC) {
			return new Damage(type.getCyrilicName());
		}
		if (type == DamageType.NO_NOSILVER) {
			return new Damage(type.getCyrilicName());
		}
		if (type == DamageType.PIERCING_GOOD) {
			return new Damage(type.getCyrilicName());
		}
		return damageType;
	}
}