package com.dnd5e.wiki.dto;

import com.dnd5e.wiki.model.stock.Equipment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EquipmentDto {
	private String name;
	private String cost;
	private float weight;
	private String description;
	private String type;
	private String book;

	
	public EquipmentDto(Equipment equipment) {
		name = equipment.getName();
		cost = equipment.getCost() + " " + equipment.getCurrency().getName();
		weight = equipment.getWeight();
		description = equipment.getDescription() == null ? "Нет описания" : equipment.getDescription();
		type = equipment.getType() == null ? "" : equipment.getType().getCyrilicName();
		book = equipment.getBook().getName();
	}
}