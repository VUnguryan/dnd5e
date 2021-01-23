package com.dnd5e.wiki.dto;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.hero.Option;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OptionDto {
	private String name;
	private String englishName;
	private String optionType;
	private String type;
	private String prerequisite;
	private Integer level;
	private String description;
	private String book;
	
	public OptionDto(Option option) {
		name = StringUtils.capitalizeWords(option.getName().toLowerCase())
				.replace(" И ", " и ").replace(" Или ", " или ").replace(" За ", " за ").replace(" С ", " с ").replace(" На ", " на ").replace(" От ", " от ").replace(" По ", " по ")
				.replace(" Над ", " над ").replace(" В ", " в ");
		englishName = option.getEnglishName();
		optionType = option.getOptionType().getShortName();
		type = option.getOptionType().getName();
		prerequisite = option.getPrerequisite();
		level = option.getLevel();
		description = option.getDescription();
		book = option.getBook().getName();
	}
}