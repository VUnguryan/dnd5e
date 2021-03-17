package com.dnd5e.wiki.controller.rest.model.xml;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.creature.ActionType;
import com.dnd5e.wiki.model.creature.ArmorType;
import com.dnd5e.wiki.model.creature.Creature;
import com.dnd5e.wiki.model.creature.CreatureFeat;
import com.dnd5e.wiki.model.creature.HabitatType;

@XmlRootElement(name = "monster")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreatureVO {
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
	private List<TraitVO> trait;
	@XmlElement
	private List<ActionVO> action;
	@XmlElement
	private List<ReactionVO> reaction;
	@XmlElement
	private List<Legendary> legendary;
	@XmlElement(required = false)
	private String description;
	@XmlElement(name = "spells", required = false)
	private String spells;
	@XmlElement(required = false)
	private String slots;
	@XmlElement(required = false)
	private String environment;
	
	@XmlElement(name = "text", required = false)
	private String source;
	public CreatureVO() {
	}

	public CreatureVO(Creature creature) {
		this.name = creature.getName();
		this.size = "" + creature.getSize().name().charAt(0);
		this.type = creature.getType().getCyrilicName();
		this.alignment = creature.getAlignment().getCyrilicName();
		this.ac = String.valueOf(creature.getAC())
				+ (!creature.getArmorTypes().isEmpty() ? "(" + creature.getArmorTypes().stream().map(ArmorType::getCyrillicName).collect(Collectors.joining(", ")) + ")" : "");
		this.hp = creature.getHp();
		this.speed = String.valueOf(creature.getSpeed()) + " фт.";
		if (creature.getFlySpeed() != null) {
			this.speed += ", летая " + creature.getFlySpeed() + " фт.";
		}
		if (creature.getClimbingSpeed() != null) {
			this.speed += ", ползая " + creature.getClimbingSpeed() + " фт.";
		}
		if (creature.getDiggingSpeed() != null) {
			this.speed += ", копая " + creature.getDiggingSpeed() + " фт.";
		}
		this.str = creature.getStrength();
		this.dex = creature.getDexterity();
		this.con = creature.getConstitution();
		this.intl = creature.getIntellect();
		this.wis = creature.getWizdom();
		this.cha = creature.getCharisma();
		this.save = creature.getSavingThrows().stream()
				.map(s -> String.format("%s %s%d", StringUtils.capitalize(s.getAbility().name().toLowerCase()),
						s.getBonus() >= 0 ? "+" : "-", s.getBonus()))
				.collect(Collectors.joining(","));
		this.skill = creature.getSkills().stream().map(s -> s.getText()).collect(Collectors.joining(", "));
		this.resist = creature.getResistanceDamages().stream().map(r -> r.getCyrilicName())
				.collect(Collectors.joining(", "));
		this.vulnerable = creature.getVulnerabilityDamages().stream().map(v -> v.getCyrilicName())
				.collect(Collectors.joining(", "));
		this.immune = creature.getImmunityDamages().stream().map(v -> v.getCyrilicName())
				.collect(Collectors.joining(","));
		this.conditionImmune = creature.getImmunityStates().stream().map(v -> v.getCyrilicName())
				.collect(Collectors.joining(", "));
		this.passive = creature.getPassivePerception();
		this.senses = creature.getSense();
		this.languages = creature.getLanguages().stream().map(l -> l.getName()).collect(Collectors.joining(", "));
		this.cr = creature.getChallengeRating();
		this.trait = creature.getFeats().stream()
				.map(f -> new TraitVO(f.getName(), f.getDescription()))
				.collect(Collectors.toList());

		this.action = creature.getActions().stream()
				.map(a -> new ActionVO(a.getName(), a.getDescription()))
				.collect(Collectors.toList());
		this.reaction = creature.getReactions().stream()
				.map(a -> new ReactionVO(a.getName(), Compendium.removeHtml(a.getDescription())))
				.collect(Collectors.toList());
		this.legendary = creature.getLegendaries().stream()
				.map(a -> new Legendary(a.getName(), Compendium.removeHtml(a.getDescription())))
				.collect(Collectors.toList());
		//this.description = Conpendium.removeHtml(creature.getDescription());
		for (CreatureFeat trait : creature.getFeats()) {
			if (trait.getName().contains("Колдовство") || trait.getName().contains("колдовство")
					|| trait.getName().contains("Использование заклинаний") || trait.getName().contains("Колдовство")) {
				String descr = Compendium.removeHtml(trait.getDescription());
				int index = descr.indexOf("Заговоры (неограниченно):");
				if (index >= 0) {
					parseSpell(descr.substring(index + "Заговоры (неограниченно):".length()));
				}
				index = descr.indexOf("Неограниченно:");
				if (index >= 0) {
					parseSpell(descr.substring(index + "Неограниченно:".length()));
				}
			}
		}
		source = "Источник: " + creature.getBook().getName();
		environment = creature.getHabitates().stream().filter(HabitatType::isXml).map(HabitatType::getXmlName).collect(Collectors.joining(", "));
	}

	private void parseSpell(String descr) {
		descr = descr.replaceAll("\\[[^\\]]*\\]", "");
		descr = descr.replace(".", "");
		descr = descr.replace("*", "");
		Pattern p = Pattern.compile("(\\d.*?:)");
		Matcher m = p.matcher(descr);
		int[] slots = new int[9];
		while (m.find()) {
			for (int i = 0; i < m.groupCount(); i++) {
				int start = m.group(i).indexOf("(");
				int slotsCount = Character.getNumericValue(m.group(i).charAt(start + 1));
				int startLevel = Character.getNumericValue(m.group(i).charAt(0)) - 1;
				slots[startLevel] = slotsCount;
				if (m.group(i).charAt(1) == '–') {
					for (int j = startLevel + 1; j < Character.getNumericValue(m.group(i).charAt(2)); j++) {
						slots[j] = slotsCount;
					}
				}
			}
		}
		this.slots = Arrays.stream(slots).mapToObj(String::valueOf).collect(Collectors.joining(","));

		descr = descr.replaceAll("(\\d{1}.*?:)", ", ");
		this.spells = Arrays
				.stream(descr.split(","))
				.map(s -> s.replaceAll("\\([^\\]]*\\)", ""))
				.map(String::trim)
				.map(String::toLowerCase)
				.map(StringUtils::capitalize)
				.collect(Collectors.joining(", "));
	}
}