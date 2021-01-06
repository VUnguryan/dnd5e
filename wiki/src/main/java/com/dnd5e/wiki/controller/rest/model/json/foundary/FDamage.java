package com.dnd5e.wiki.controller.rest.model.json.foundary;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.dnd5e.wiki.model.creature.Creature;
import com.dnd5e.wiki.model.creature.DamageType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FDamage {
	private List<String> value;
	private String custom ="";
	public FDamage(Creature creature, Supplier<List<DamageType>> values) {
		value = values.get().stream().map(DamageType::name).map(String::toLowerCase).collect(Collectors.toList());
	}
}