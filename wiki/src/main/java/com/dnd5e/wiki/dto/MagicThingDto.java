package com.dnd5e.wiki.dto;

import java.util.stream.Collectors;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.hero.classes.HeroClass;
import com.dnd5e.wiki.model.stock.Armor;
import com.dnd5e.wiki.model.stock.Weapon;
import com.dnd5e.wiki.model.treasure.MagicThing;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MagicThingDto {
	private String name;
	private String englishName;
	private String type;
	private String adType;

	private String rarity;
	private boolean customization;
	private String custSpecial;

	private String special;
	private String custClasses;
	private int cost;
	private String description;
	private String book;

	public MagicThingDto(MagicThing magicThing) {
		name = StringUtils.capitalizeWords(magicThing.getName().toLowerCase())
				.replace(" И ", " и ").replace(" Или ", " или ").replace(" За ", " за ").replace(" С ", " с ").replace(" На ", " на ").replace(" От ", " от ").replace(" По ", " по ")
				.replace(" Над ", " над ").replace(" В ", " в ").replace(" Из ", " из ");
		englishName = magicThing.getEnglishName();
		type = magicThing.getType().getCyrilicName();
		if (!magicThing.getWeapons().isEmpty()) {
			adType = magicThing.getWeapons().stream().map(Weapon::getName).map(String::toLowerCase).collect(Collectors.joining(", "));
		}
		if (!magicThing.getArmors().isEmpty()) {
			adType = magicThing.getArmors().stream().map(Armor::getName).map(String::toLowerCase).collect(Collectors.joining(", "));
		}
		if (magicThing.getSpecial() != null) {
			adType = magicThing.getSpecial();
		}
		rarity = magicThing.getRarity().getCyrilicName(); 
		customization = magicThing.getCustomization();
		custSpecial = magicThing.getCustSpecial();
		custClasses = magicThing.getCustClasses().stream().map(HeroClass::getName).map(c-> "ЧАРОДЕЙ".equals(c) ? "чародеем": c + "ом").map(String::toLowerCase).collect(Collectors.joining(", "));
		cost = magicThing.getCost();
		description = magicThing.getDescription();
		book = magicThing.getBook().getName();
	}
}