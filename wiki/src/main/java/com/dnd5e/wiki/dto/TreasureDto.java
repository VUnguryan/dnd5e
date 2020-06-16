package com.dnd5e.wiki.dto;

import com.dnd5e.wiki.model.treasure.Treasure;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TreasureDto {
	private String name;
	private int cost;
	private String type;
	
	public TreasureDto(Treasure treasure) {
		name = treasure.getName();
		type = treasure.getType().getName();
		cost = treasure.getCost();
	}
}