package com.dnd5e.wiki.controller.rest.model.json.shaped;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.dnd5e.wiki.model.creature.DamageType;
import com.dnd5e.wiki.model.spell.Spell;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class SAttack {
	private String type;
	private String damage;
	private String damageType;
	private Integer higherLevelDice;
	public SAttack(Spell spell, String description) {
		if (description.toLowerCase().contains("дальнобойную атаку заклинанием")) {
			type = "ranged";	
		} else if(description.toLowerCase().contains("рукопашную атаку заклинанием")) {
			type = "melee";
		}
		damageType = spell.getDamageType().stream().map(DamageType::getCyrilicName).collect(Collectors.joining(", "));
		Pattern pattern = Pattern.compile("\\d+к\\d+");
	    Matcher matcher = pattern.matcher(description);
	    if (matcher.find()) {
	    	damage = description.substring(matcher.start(), matcher.end()).replace('к', 'd');
	    }
	    if(spell.getUpperLevel()!= null) {
	    	matcher = pattern.matcher(spell.getUpperLevel());
	    	if (matcher.find()) {
	    		String dmg = spell.getUpperLevel().substring(matcher.start(), matcher.end());
	    		higherLevelDice = Integer.valueOf(dmg.substring(0, 1));
	    	}
	    }
	}
}
