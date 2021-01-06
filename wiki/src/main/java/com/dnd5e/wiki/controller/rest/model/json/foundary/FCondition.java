package com.dnd5e.wiki.controller.rest.model.json.foundary;

import java.util.List;
import java.util.stream.Collectors;

import com.dnd5e.wiki.model.creature.Condition;
import com.dnd5e.wiki.model.creature.Creature;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FCondition {
	private List<String> value;
	private String custom ="";
	public FCondition(Creature creature) {
		value = creature.getImmunityStates().stream().map(Condition::name).map(String::toLowerCase).collect(Collectors.toList());
	}
}