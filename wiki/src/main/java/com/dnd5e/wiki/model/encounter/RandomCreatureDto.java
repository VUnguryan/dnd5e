package com.dnd5e.wiki.model.encounter;

import com.dnd5e.wiki.model.creature.Creature;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RandomCreatureDto {
	private int count;
	private Creature creature;
}