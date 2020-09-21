package com.dnd5e.wiki.dto;

import java.util.stream.Collectors;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.creature.Language;
import com.dnd5e.wiki.model.creature.SkillType;
import com.dnd5e.wiki.model.hero.Background;
import com.dnd5e.wiki.model.stock.Equipment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BackgroundDto {
	private Integer id;
	private String name;
	private String englishName;
	private String description;
	private String skillDescription;
	private String skillName;
	private String skills;
	private String equipments;
	private String toolOwnership;
	private String languages;
	private int startMoney;
	private String book;
	
	public BackgroundDto(Background background) {
		id = background.getId();
		name = StringUtils.capitalizeWords(background.getName().toLowerCase())
				.replace(" И ", " и ").replace(" Или ", " или ").replace(" За ", " за ").replace(" С ", " с ").replace(" На ", " на ").replace(" От ", " от ").replace(" По ", " по ")
				.replace(" Над ", " над ").replace(" В ", " в ");
		englishName = background.getEnglishName();
		skillName = background.getSkillName();
		skills = background.getSkills().stream().map(SkillType::getCyrilicName).collect(Collectors.joining(", "));
		background.getEquipments();
		description = background.getDescription();
		skillDescription = background.getSkillDescription();
		toolOwnership = background.getToolOwnership();
		equipments = background.getEquipments().stream().map(Equipment::getName).collect(Collectors.joining(", "));
		languages = (background.getLanguage() == null ? "" : background.getLanguage()) + background.getLanguages().stream().map(Language::getName).collect(Collectors.joining(", "));
		startMoney = background.getStartMoney();
		book = background.getBook().getName();
	}
}