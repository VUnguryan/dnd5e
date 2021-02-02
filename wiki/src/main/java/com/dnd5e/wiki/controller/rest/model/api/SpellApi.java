package com.dnd5e.wiki.controller.rest.model.api;

import java.util.List;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.spell.Spell;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpellApi {
	private int id;
	private String name;
	private String englishName;
	private String source;
	private Integer page;
	private Boolean srd;
	private byte level;
	private String school;
	private List<SpellTimeApi> time;
	private RangeApi range;
	private ComponentsApi components;
	private DurationApi duration;
	private List<String> entries;
	private EntriesHigherLevelApi entriesHigherLevel;
	private List<String> damageInflict;
	private List<String> conditionInflict;
	private List<String> savingThrow;
	private List<String> abilityCheck;
	
	public SpellApi(Spell spell) {
		id = spell.getId();
		name = StringUtils.capitalizeWords(spell.getName().toLowerCase())
				.replace(" И ", " и ").replace(" Или ", " или ").replace(" За ", " за ").replace(" С ", " с ").replace(" На ", " на ").replace(" От ", " от ").replace(" По ", " по ")
				.replace(" Над ", " над ").replace(" В ", " в ");
		englishName = spell.getEnglishName();
		level = spell.getLevel();
		switch (spell.getSchool()) {
		case ABJURATION:
			school = "A";
			break;
		case EVOCATION:
			school = "V";
			break;
		case ENCHANTMENT:
			school = "E";
			break;
		case CONJURATION:
			school = "C";
			break;
		case DIVINATION:
			school = "D";
			break;
		case ILLUSION:
			school = "I";
			break;
		case NECROMANCY:
			school = "N";
			break;
		case TRANSMUTATION:
			school = "T";
			break;
		}
	}
}