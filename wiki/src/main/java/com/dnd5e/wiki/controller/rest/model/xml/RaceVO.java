package com.dnd5e.wiki.controller.rest.model.xml;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.bind.annotation.XmlElement;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.hero.race.Race;

import lombok.Getter;

@Getter
public class RaceVO {
	@XmlElement
	private String name;

	@XmlElement
	private String size;

	@XmlElement
	private Integer speed;
	
	@XmlElement (required = false)
	private String ability;
	
	@XmlElement
	private List<TraitVO> trait;

	@XmlElement(name = "text")
	private String source;
	
	@XmlElement(name = "modifier", required = false)
	private List<ModifierVO> modifiers;
	
	public RaceVO(Race race) {
		name = StringUtils.capitalize(race.getName().toLowerCase());
		size = String.valueOf(race.getSize().name().charAt(0));
		speed = race.getSpeed();
		ability = race.getFeatures().stream().flatMap(f -> f.getAbilityBonuses().stream())
				.map(r -> 
					String.format("%s %+d",	r.getAbility().getCapitalizeName(), r.getBonus())
				)
				.collect(Collectors.joining(", "));
		if (race.getParent() != null) {
			if (!ability.isEmpty())
			{
				ability += ", ";
			}
			ability += race.getParent().getFeatures().stream().flatMap(f -> f.getAbilityBonuses().stream())
				.map(r -> 
					String.format("%s %+d",	r.getAbility().getCapitalizeName(), r.getBonus())
				)
				.collect(Collectors.joining(", "));
		}
		if (race.getParent() != null) {
			
			trait = Stream.concat(race.getParent().getFeatures().stream(), race.getFeatures().stream())
					.map(TraitVO::new)
					.collect(Collectors.toList());
		}
		else
		{
			trait = race.getFeatures()
					.stream().map(TraitVO::new)
					.collect(Collectors.toList());
		}

		source = "Источник: " +  race.getBook().getName();
	}
}