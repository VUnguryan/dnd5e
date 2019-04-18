package com.dnd5e.wiki.controller.rest.model.xml;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.h2.util.StringUtils;

import com.dnd5e.wiki.model.creature.ActionType;
import com.dnd5e.wiki.model.creature.Creature;

@XmlRootElement(name = "monster")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreatureVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String HTML_REGEXP = "<\\/?[A-Za-z]+[^>]*>";

	@XmlElement
	private String name;
	@XmlElement
	private String size;
	@XmlElement
	private String type;
	@XmlElement
	private String alignment;
	@XmlElement
	private String ac;
	@XmlElement
	private String hp;
	@XmlElement
	private String speed;
	@XmlElement
	private byte str;
	@XmlElement
	private byte dex;
	@XmlElement
	private byte con;
	@XmlElement(name = "int")
	private byte intl;
	@XmlElement
	private byte wis;
	@XmlElement
	private byte cha;
	@XmlElement
	private String save;
	@XmlElement
	private String skill;
	@XmlElement
	private String resist;
	@XmlElement
	private String vulnerable;
	@XmlElement
	private String immune;
	@XmlElement
	private String conditionImmune;
	@XmlElement
	private String senses;
	@XmlElement
	private byte passive;
	@XmlElement
	private String languages;
	@XmlElement
	private String cr;
	@XmlElement
	private List<Trait> trait;
	@XmlElement
	private List<Action> action;
	@XmlElement
	private List<Reaction> reaction;
	@XmlElement
	private List<Legendary> legendary;
	@XmlElement
	private String description;
	
	public CreatureVO() {

	}

	public CreatureVO(Creature creature) {
		this.name = creature.getName();
		this.size = "" + creature.getSize().name().charAt(0);
		this.type = creature.getType().getCyrilicName();
		this.alignment = creature.getAlignment().getCyrilicName();
		this.ac = String.valueOf(creature.getAC())  
				+ (creature.getArmorType() != null ? "(" + creature.getArmorType().getCyrillicName() + ")" : "");
		this.hp = creature.getHp(); 
		this.speed = String.valueOf(creature.getSpeed()) + " фт.";
		this.str = creature.getStrength();
		this.dex = creature.getDexterity();
		this.con = creature.getConstitution();
		this.intl = creature.getIntellect();
		this.cha = creature.getCharisma();
		this.save = creature.getSavingThrows()
				.stream()
				.map(s-> String.format("%s %s%d", 
						StringUtils.cache(s.getAbility().name().toLowerCase()), s.getBonus()>=0 ? "+" : "-",s.getBonus()))
				.collect(Collectors.joining(","));
		this.skill = creature.getSkills()
				.stream()
				.map(s -> s.getText())
				.collect(Collectors.joining(", "));
		this.resist = creature.getResistanceDamages().stream().map(r-> r.getCyrilicName()).collect(Collectors.joining(", "));
		this.vulnerable = creature.getVulnerabilityDamages().stream().map(v->v.getCyrilicName()).collect(Collectors.joining(", "));
		this.immune = creature.getImmunityDamages().stream().map(v->v.getCyrilicName()).collect(Collectors.joining(","));
		this.conditionImmune = creature.getImmunityStates().stream().map(v->v.getCyrilicName()).collect(Collectors.joining(", ")); 
		this.senses = creature.getSenses();
		this.passive = creature.getPassivePerception();
		this.languages = creature.getLanguages().stream().map(l -> l.getName()).collect(Collectors.joining(", "));
		this.cr = creature.getChallengeRating();
		this.trait = creature.getFeats()
				.stream()
				.map(f -> new Trait(f.getName(), removeHtml(f.getDescription())))
				.collect(Collectors.toList());

		this.action = creature.getActions()
				.stream()
				.filter(a->a.getActionType()== ActionType.ACTION)
				.map(a -> new Action(a.getName(), removeHtml(a.getDescription())))
				.collect(Collectors.toList());
		this.reaction = creature.getActions()
				.stream()
				.filter(a->a.getActionType()== ActionType.REACTION)
				.map(a -> new Reaction(a.getName(), removeHtml(a.getDescription())))
				.collect(Collectors.toList());
		this.legendary = creature.getActions()
				.stream()
				.filter(a->a.getActionType()== ActionType.LEGENDARY)
				.map(a -> new Legendary(a.getName(), removeHtml(a.getDescription())))
				.collect(Collectors.toList());
		this.description = removeHtml(creature.getDescription());
	}


	private String removeHtml(String string) {
		return string == null ? ""
				: string.replaceAll(HTML_REGEXP, "").replace("&nbsp;", " ").replace("&mdash;", "")
						.replace("&ndash;", "").replace("&laquo;", "").replace("&raquo;", "").replace("&rsquo;", "")
						.replace("&bull;", "").replace("&times;", "").replace("&minus;", "-");
	}
}
