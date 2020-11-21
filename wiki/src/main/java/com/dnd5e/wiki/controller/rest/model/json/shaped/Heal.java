package com.dnd5e.wiki.controller.rest.model.json.shaped;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dnd5e.wiki.model.spell.Spell;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class Heal {
	private String heal;
	private Integer bonus;
	private Integer higherLevelAmount;
	private Boolean castingStat;
	private Integer higherLevelDice;
	
	public Heal(Spell spell, String description) {
		Pattern pattern = Pattern.compile("\\d+к\\d+");
	    Matcher matcher = pattern.matcher(description);
	    if (matcher.find()) {
	    	heal = description.substring(matcher.start(), matcher.end()).replace('к', 'd');
	    }

	    if (description.contains("модификатор вашей базовой характеристики") || description.contains("ваш модификатор базовой характеристики")) {
		    castingStat = true;
	    }
	    
	    pattern = Pattern.compile("\\+\\s?\\d+");
	    matcher = pattern.matcher(description);
	    if (matcher.find()) {
	    	bonus = Integer.valueOf(description.substring(matcher.start(), matcher.end()).replace("+", "").trim());
	    }
	    if (spell.getUpperLevel()!=null) {
		    pattern = Pattern.compile("\\d+к\\d+");
		    matcher = pattern.matcher(spell.getUpperLevel());
		    if (matcher.find()) {
		    	higherLevelDice = Integer.valueOf(spell.getUpperLevel().substring(matcher.start(),matcher.start()+1).trim());
		    }
	    }
	}

	public Heal(int value) {
		heal = String.valueOf(value);
		higherLevelDice = 10;
	}
}