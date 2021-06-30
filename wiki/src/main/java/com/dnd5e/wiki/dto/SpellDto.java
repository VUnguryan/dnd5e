package com.dnd5e.wiki.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.dto.user.HeroClassDto;
import com.dnd5e.wiki.model.creature.DamageType;
import com.dnd5e.wiki.model.spell.Spell;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SpellDto {
	private int id;	
	private String level;
	private String name;
	private String ritual;
	private String concentration;
	private String school;
	private String verbalComponent;
	private String somaticComponent;
	private String materialComponent;
	private String components;
	private String timeCast;
	private String distance;
	private String duration;
	private String description;
	private String upperLevel;
	private String book;
	private String damageType;
	private List<HeroClassDto> heroClass;
	private List<ArchitypeDto> subClass;
	private String englishName;
	private Boolean consumable = false;
	
	public SpellDto(Spell spell) {
		id = spell.getId();
		level = spell.getLevel() == 0 ? "Заговор" : String.valueOf(spell.getLevel());
		name = StringUtils.capitalizeWords(spell.getName().toLowerCase())
				.replace(" И ", " и ").replace(" Или ", " или ").replace(" За ", " за ").replace(" С ", " с ").replace(" На ", " на ").replace(" От ", " от ").replace(" По ", " по ")
				.replace(" Над ", " над ").replace(" В ", " в ");
		ritual = String.valueOf(spell.getRitual());
		concentration = String.valueOf(spell.getConcentration());
		school = spell.getSchool().getName();
		timeCast = spell.getTimeCast();
		distance = spell.getDistance();
		duration = spell.getDuration();
		verbalComponent = spell.getVerbalComponent() !=null && spell.getVerbalComponent() ? "★" : "";
		somaticComponent = spell.getSomaticComponent() != null  && spell.getSomaticComponent() ? "★": "";
		materialComponent = spell.getMaterialComponent() != null && spell.getMaterialComponent() ? "★": "";
		components = spell.getAdditionalMaterialComponent();
		consumable = spell.getConsumable();
		damageType = spell.getDamageType().isEmpty() ? "" : spell.getDamageType().stream().map(DamageType::getCyrilicName).collect(Collectors.joining(", "));
		englishName = spell.getEnglishName();
		book = spell.getBook().getName() + (spell.getPage() != null ? ", стр. " + spell.getPage() : "");
	}
}