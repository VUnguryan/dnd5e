package com.dnd5e.wiki.controller.rest.model.xml;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.hero.classes.HeroClass;
import com.dnd5e.wiki.model.spell.Spell;

import lombok.Getter;

@Getter
public class SpellVO {
	@XmlElement
	private String name;
	@XmlElement
	private byte level;
	@XmlElement
	private String school;
	@XmlElement
	private String time;
	@XmlElement
	private String range;
	@XmlElement
	private String components;
	@XmlElement
	private String duration;
	@XmlElement(required = false)
	private String ritual;
	@XmlElement
	private List<String> text;

	@XmlElement(name = "text", required = false)
	private String source;
	@XmlElement
	private String classes;

	public SpellVO() {

	}

	public SpellVO(Spell spell) {
		this.name = StringUtils.capitalize(spell.getName().toLowerCase()).trim();
		this.level = spell.getLevel();
		switch (spell.getSchool()) {
		case ABJURATION:
			this.school = "A";
			break;
		case CONJURATION:
			this.school = "C";
			break;
		case DIVINATION:
			this.school = "D";
			break;			
		case ENCHANTMENT:
			this.school = "EN";
			break;			
		case EVOCATION:
			this.school = "EV";
			break;			
		case ILLUSION:
			this.school = "I";
			break;
		case NECROMANCY:
			this.school = "N";
			break;
		case TRANSMUTATION:
			this.school = "T";
			break;
		default:
			break;
		}
		this.time = spell.getTimeCast();
		this.range = spell.getDistance();
		this.components = isBoolean(spell.getVerbalComponent()) ? "V" : "";
		this.components += !this.components.isEmpty() && isBoolean(spell.getSomaticComponent()) ? ", S" : isBoolean(spell.getSomaticComponent()) ? "S": "";
		this.components += !this.components.isEmpty() && isBoolean(spell.getMaterialComponent()) ? ", M" : ""; 
		this.components += spell.getAdditionalMaterialComponent() != null ? " (" + spell.getAdditionalMaterialComponent() + ")"  :"";
		this.duration = spell.getDuration();
		if (spell.getRitual()) {
			this.ritual = "YES";
		}
		this.text = Arrays.stream(spell.getDescription().split("<p>"))
				.filter(Objects::nonNull)
				.map(String::trim)
				.filter(s -> !s.isEmpty())
				.map(Compendium::removeHtml)
				.map(String::trim)
				.collect(Collectors.toList());
		if (spell.getBook() != null)
		{
			this.source = "Источник: " + spell.getBook().getName(); 
		}

		this.classes = spell.getHeroClass().stream()
				.distinct()
				.map(c -> toEnglishName(c)).distinct().filter(t->!t.isEmpty())
				.collect(Collectors.joining(", "));
	}

	private boolean isBoolean(Boolean b) {
		return b == null ? false : b;
	}

	private String toEnglishName(HeroClass heroClass) {
		switch(heroClass.getName()) {
			case "БАРД": return "Bard";
			case "ВОЛШЕБНИК": return "Wizard";
			case "ДРУИД": return "Druid";
			case "ЖРЕЦ": return "Cleric";
			case "КОЛДУН": return "Warlock";
			case "ПАЛАДИН": return "Paladin";
			case "СЛЕДОПЫТ": return "Ranger";
			case "ВАРВАР": return "Barbarian";
			case "ВОИН": return "Fighter";
			case "МОНАХ": return "Monk";
			case "ПЛУТ": return "Rogue";
		}
		return "";
	}
}