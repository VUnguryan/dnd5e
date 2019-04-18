package com.dnd5e.wiki.controller.rest.model.xml;

import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.hero.classes.HeroClass;
import com.dnd5e.wiki.model.spell.Spell;

import lombok.Getter;

@Getter
public class SpellVO {
	private static final String HTML_REGEXP = "<\\/?[A-Za-z]+[^>]*>";
	
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
	private String text;
	@XmlElement
	private String classes;

	public SpellVO() {

	}

	public SpellVO(Spell spell) {
		this.name = StringUtils.capitalize(spell.getName().toLowerCase());
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
		this.components = (spell.getVerbalComponent() ? "V" : "");
		this.components += !this.components.isEmpty() && spell.getSomaticComponent() ? ", S" : spell.getSomaticComponent() ? "S": "";
		this.components += !this.components.isEmpty() && spell.getMaterialComponent() ? ", M" : ""; 
		this.components += spell.getAdditionalMaterialComponent()!= null ? " (" + spell.getAdditionalMaterialComponent() + ")"  :"";
		this.duration = spell.getDuration();
		if (spell.getRitual()) {
			this.ritual = "YES";
		}
		this.text = removeHtml(spell.getDescription());
		this.classes = spell.getHeroClass().stream().distinct().map(c -> toEnglishName(c)).distinct().filter(t->!t.isEmpty()).collect(Collectors.joining(", "));

	}
	private String removeHtml(String string) {
		return string == null ? ""
				: string.replaceAll(HTML_REGEXP, "").replace("&nbsp;", " ").replace("&mdash;", "")
						.replace("&ndash;", "").replace("&laquo;", "").replace("&raquo;", "").replace("&rsquo;", "")
						.replace("&bull;", "").replace("&times;", "").replace("&minus;", "-");
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
