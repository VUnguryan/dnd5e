package com.dnd5e.wiki.dto;

import com.dnd5e.wiki.model.Rule;

import lombok.Getter;

@Getter
public class RuleDto {
	private String name;
	private String englishName;
	private String description;
	private String type;
	private String source;
	public RuleDto(Rule rule) {
		name = rule.getName();
		englishName = rule.getEnglishName();
		description = rule.getDescription();
		type = rule.getType();
		source = rule.getBook().getName(); 
		if (rule.getPage() != null)  {
			source += ", стр. " + rule.getPage();
		}
	}
}