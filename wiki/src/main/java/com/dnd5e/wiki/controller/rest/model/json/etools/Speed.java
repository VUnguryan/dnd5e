package com.dnd5e.wiki.controller.rest.model.json.etools;

import com.dnd5e.wiki.model.creature.Creature;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "walk", "fly", "swim" })
public class Speed {

	@JsonProperty("walk")
	private Byte walk;
	@JsonProperty("fly")
	private Short fly;
	@JsonProperty("swim")
	private Short swim;
	@JsonProperty("burrow")
	private Short burrow;
	

	public Speed(Creature creature) {
		walk = creature.getSpeed();
		if (creature.getFlySpeed()!= null) {
			fly = creature.getFlySpeed();
		}
		if (creature.getSwimmingSpped()!=null) {
			swim = creature.getSwimmingSpped();
		}
		if (creature.getDiggingSpeed() != null) {
			burrow = creature.getDiggingSpeed();
		}
	}
}