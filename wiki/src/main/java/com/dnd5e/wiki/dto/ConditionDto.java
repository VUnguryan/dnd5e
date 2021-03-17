package com.dnd5e.wiki.dto;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.hero.Condition;

import lombok.Getter;

@Getter
public class ConditionDto {
	private String name;
	private String englishName;
	private String description;
	private String type;
	private String source;
	public ConditionDto(Condition condition) {
		name = StringUtils.capitalizeWords(condition.getName().toLowerCase());
		englishName = condition.getEnglishName();
		description = condition.getDescription();
		type = condition.getType().getName();
		source = condition.getBook().getName(); 
		if (condition.getPage() != null)  {
			source += ", стр. " + condition.getPage();
		}
	}
}