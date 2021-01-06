package com.dnd5e.wiki.controller.rest.model.json.foundary;

import java.util.ArrayList;
import java.util.List;

import com.dnd5e.wiki.model.creature.Creature;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FCreature {
	private String name;
	private String type;
	private FData data;
	private List<FItem> items = new ArrayList<FItem>();
	public FCreature(Creature creature) {
		name = creature.getName();
		type = "npc";
		data = new FData(creature);
		creature.getActions().stream().map(FItem::new).forEach(i -> items.add(i));
		creature.getFeats().stream().map(FItem::new).forEach(i -> items.add(i));
	}
}