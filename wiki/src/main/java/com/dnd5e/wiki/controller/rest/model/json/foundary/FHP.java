package com.dnd5e.wiki.controller.rest.model.json.foundary;

import com.dnd5e.wiki.model.creature.Creature;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FHP {
    private short value;
    private short min;
    private short max;
    private byte temp;
    private byte tempmax;
    private String formula;

	public FHP(Creature creature) {
		value = creature.getAverageHp();
		max = creature.getAverageHp();
		formula = creature.getHpFormula();	
	}
}