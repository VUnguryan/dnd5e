package com.dnd5e.wiki.dto;

import java.util.Random;
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
	private Integer id;
	private String name;
	private String englishName;
	private String type;
	private String adType;

	private String rarity;
	private boolean customization;
	private String custSpecial;

	private String special;
	private String custClasses;
	private String cost;
	private String description;
	private String book;

	public MagicThingDto(MagicThing magicThing) {
		id = magicThing.getId();
		name = StringUtils.capitalizeWords(magicThing.getName().toLowerCase()).replace(" И ", " и ")
				.replace(" Или ", " или ").replace(" За ", " за ").replace(" С ", " с ").replace(" На ", " на ")
				.replace(" От ", " от ").replace(" По ", " по ").replace(" Над ", " над ").replace(" В ", " в ")
				.replace(" Из ", " из ");
		englishName = magicThing.getEnglishName();
		type = magicThing.getType().getCyrilicName();
		if (!magicThing.getWeapons().isEmpty()) {
			adType = magicThing.getWeapons().stream().map(Weapon::getName).map(String::toLowerCase)
					.collect(Collectors.joining(", "));
		}
		if (!magicThing.getArmors().isEmpty()) {
			adType = magicThing.getArmors().stream().map(Armor::getName).map(String::toLowerCase)
					.collect(Collectors.joining(", "));
		}
		if (magicThing.getSpecial() != null) {
			adType = magicThing.getSpecial();
		}
		rarity = magicThing.getRarity().getCyrilicName();
		customization = magicThing.getCustomization();
		custSpecial = magicThing.getCustSpecial();
		custClasses = magicThing.getCustClasses().stream().map(HeroClass::getAblativeName).map(String::toLowerCase)
				.collect(Collectors.joining(", "));
		String rangeCost;
		switch (magicThing.getRarity()) {
		case COMMON:
			if (magicThing.isConsumed()) {
				rangeCost = "от 10 до 35 зм.";
			} else {
				rangeCost = "от 20 до 70 зм.";
			}
			break;
		case UNCOMMON:
			if (magicThing.isConsumed()) {
				rangeCost = "от 100 до 350 зм.";
			} else {
				rangeCost = "от 200 до 700 зм.";
			}
			break;
		case RARE:
			rangeCost = "от 2 000 до 20 000 зм";
			if (magicThing.isConsumed()) {
				rangeCost = "от 1 000 до 10 000 зм";
			}
			break;
		case VERY_RARE:
			rangeCost = "от 20 000 до 50 000 зм.";
			if (magicThing.isConsumed()) {
				rangeCost = "от 10 000 до 25 000 зм.";
			}
			break;
		case LEGENDARY:
			rangeCost = "от 50 000 до 300 000 зм.";
			if (magicThing.isConsumed()) {
				rangeCost = "от 25 000 до 150 000 зм.";
			}
			break;
		case ARTIFACT:
			rangeCost = "от 300 000 зм. до невозможно купить";
			break;
		default:
			rangeCost = Integer.toString(magicThing.getCost());
			break;
		}
		cost = rangeCost;
		description = magicThing.getDescription();
		book = magicThing.getBook().getName();
	}

	public int getRandomCost() {
		Random rnd = new Random();
		switch (rarity) {
		case "обычный":
			return (rnd.nextInt(6) + 2) * 10;
		case "необычный":
			return (rnd.nextInt(6) + 2) * 100;
		case "редкий":
			return (rnd.nextInt(10) + rnd.nextInt(10) + 2) * 1000;
		case "очень редкий":
			return (rnd.nextInt(4) + 2) * 10_000;
		case "легендарный":
			return (rnd.nextInt(6) + rnd.nextInt(6) + 2) * 25000;
		default:
			return 300000*(1 + rnd.nextInt(10));
		}
	}

	public void setCost(int cost) {
		this.cost = String.valueOf(cost); 
	}
}