package com.dnd5e.wiki.dto;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.hero.ArchetypeTrait;
import com.dnd5e.wiki.model.hero.HeroClassTrait;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClassFetureDto {
	private int id;
	private String name;
	private byte level;
	private byte order;
	private String type;
	private String prefix;
	private String description;

	public ClassFetureDto(HeroClassTrait feature, String className) {
		id = feature.getId();
		name = StringUtils.capitalizeWords(feature.getName().toLowerCase()).replace(" И ", " и ")
				.replace(" Или ", " или ").replace(" За ", " за ").replace(" С ", " с ").replace(" На ", " на ")
				.replace(" От ", " от ").replace(" По ", " по ").replace(" Над ", " над ").replace(" В ", " в ");
		level = feature.getLevel();
		description = feature.getDescription();
		type = String.valueOf(feature.getLevel());
		switch (feature.getLevel()) {
		case 2:
		case 6:
		case 7:
		case 8:
			type += "-ой";
			break;
		case 3:
			type += "-ий";
			break;
		default:
			type += "-ый";
			break;
		}
		prefix ="c";
		type+= " уровень, умение класса " + StringUtils.capitalizeWords(className.toLowerCase());
		order = 1;
	}
	
	public ClassFetureDto(ArchetypeTrait feature, String archetypeName) {
		id = feature.getId();
		name = StringUtils.capitalizeWords(feature.getName().toLowerCase()).replace(" И ", " и ")
				.replace(" Или ", " или ").replace(" За ", " за ").replace(" С ", " с ").replace(" На ", " на ")
				.replace(" От ", " от ").replace(" По ", " по ").replace(" Над ", " над ").replace(" В ", " в ");
		level = feature.getLevel();
		description = feature.getDescription();
		type = String.valueOf(feature.getLevel());
		switch (feature.getLevel()) {
		case 2:
		case 6:
		case 7:
		case 8:
			type += "-ой";
			break;
		case 3:
			type += "-ий";
			break;
		default:
			type += "-ый";
			break;
		}
		prefix ="a";
		type += " уровень, умение " + archetypeName;
		order = 2;
	}
}