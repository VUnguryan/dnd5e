package com.dnd5e.wiki.controller.rest.model.json.foundary;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FItemDamage {
	private List<List<String>> parts =new ArrayList<List<String>>();
	public void addDamage(String formula, String damageType) {
		List<String> damage = new ArrayList<String>();
		damage.add(formula);
		damage.add(damageType);
		parts.add(damage);
	}
}