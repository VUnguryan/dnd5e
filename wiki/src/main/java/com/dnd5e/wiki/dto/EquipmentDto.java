package com.dnd5e.wiki.dto;

import java.util.stream.Collectors;

import com.dnd5e.wiki.model.stock.Equipment;
import com.dnd5e.wiki.model.stock.EquipmentType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EquipmentDto {
	private String name;
	private String englishName;
	private String cost;
	private String weight;
	private String description;
	private String type;
	private String book;
	
	public EquipmentDto(Equipment equipment) {
		name = equipment.getName();
		englishName = equipment.getEnglishName();
		if (equipment.getCost() == null) {
			cost = "&mdash;";
		}
		else
		{
			cost = equipment.getCost() + " " + equipment.getCurrency().getName();
			switch (equipment.getCurrency()) {
			case SM:
				cost=  String.valueOf(equipment.getCost() / 10f) + " " + equipment.getCurrency().getName();
				break;
			case GM:
				cost=  String.valueOf(equipment.getCost() / 100f) + " " + equipment.getCurrency().getName();
				break;
			case PM:
				cost=  String.valueOf(equipment.getCost() / 1000f) + " " + equipment.getCurrency().getName();
				break;
			default:
				break;
			}
		}
		weight = (equipment.getWeight() == null ? "&mdash;": String.valueOf(equipment.getWeight()));
		description = equipment.getDescription() == null ? "Нет описания" : equipment.getDescription();
		type = equipment.getTypes().stream().map(EquipmentType::getCyrilicName).collect(Collectors.joining(", "));
		book = equipment.getBook().getName();
	}
}