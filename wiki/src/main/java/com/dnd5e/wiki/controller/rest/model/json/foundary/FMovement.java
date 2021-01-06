package com.dnd5e.wiki.controller.rest.model.json.foundary;

import com.dnd5e.wiki.model.creature.Creature;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FMovement {
	private short burrow;
    private short climb;
    private short fly;
    private short swim;
    private short walk;
    private String units = "ft";
    private String hover = "false";
    public FMovement(Creature creature) {
		walk = creature.getSpeed();
		if (creature.getClimbingSpeed()!=null) {
			climb = creature.getClimbingSpeed();
		}
		if (creature.getFlySpeed()!=null) {
			fly = creature.getFlySpeed();
		}
		if (creature.getSwimmingSpped()!=null) {
			swim = creature.getSwimmingSpped();
		}
		if (creature.getDiggingSpeed()!=null) {
			burrow = creature.getDiggingSpeed();
		}
		if (creature.getHover()!=null && creature.getHover() == 1 ) {
			hover = "true";
		}
	}
}
