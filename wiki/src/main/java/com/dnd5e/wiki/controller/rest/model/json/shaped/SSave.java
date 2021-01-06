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
public class SSave {
	private String ability;
	private String damage;
	private String damageType;
	private String secondaryDamage;
	private String secondaryDamageType;

	private String saveSuccess;

	public SSave(Spell spell, String description) {
		if (!spell.getDamageType().isEmpty())
		{
			damageType = spell.getDamageType().stream().map(DamageType::getCyrilicName).collect(Collectors.joining(", "));			
		}
		if (description.contains("спасброске Силы") || description.contains("спасброск Силы")) {
			ability = "Strength";
		}
		if (description.contains("спасброске Ловкости") || description.contains("спасбросок Ловкости")) {
			ability = "Dexterity";
		}
		if (description.contains("спасбросоке Телосложения") || description.contains("спасбросок Телосложения")) {
			ability = "Constitution";
		}
		if (description.contains("спасбросоке Мудрости") || description.contains("спасбросок Мудрости")) {
			ability = "Wisdom";
		}
		if (description.contains("спасбросоке Харизмы") || description.contains("спасбросок Харизмы")) {
			ability = "Charisma";
		}
		Pattern pattern = Pattern.compile("\\d+к\\d+");
	    Matcher matcher = pattern.matcher(description);
	    if (matcher.find()) {
	    	damage = description.substring(matcher.start(), matcher.end()).replace('к', 'd');
	    }
	}
}
