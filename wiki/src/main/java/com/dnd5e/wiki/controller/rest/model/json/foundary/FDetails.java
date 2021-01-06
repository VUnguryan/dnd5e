package com.dnd5e.wiki.controller.rest.model.json.foundary;

import java.util.stream.Collectors;

import com.dnd5e.wiki.model.creature.Creature;
import com.dnd5e.wiki.model.creature.HabitatType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FDetails {
	private FBiography biography;
	private String alignment;
	private String race;
	private String type;
	private String environment;
	private float cr;
	private byte spellLevel;
	private String source;
	public FDetails(Creature creature) {
		biography = new FBiography(creature.getDescription(), "");
		alignment = creature.getAlignment().getCyrilicName();
		race = creature.getRaceName();
		type = creature.getType().getCyrilicName();
		environment = creature.getHabitates().stream().map(HabitatType::getName).collect(Collectors.joining(", "));
		switch (creature.getChallengeRating()) {
		case "1/2":
			cr = 1f/2;
			break;
		case "1/4":
			cr = 1f/4;
			break;
		case "1/8":
			cr = 1f/8;
			break;
		default:
			cr = Integer.valueOf(creature.getChallengeRating());
		}
		source = creature.getBook().getSource();
	}
}
