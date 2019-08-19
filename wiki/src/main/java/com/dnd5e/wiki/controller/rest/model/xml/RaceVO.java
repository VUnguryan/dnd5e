package com.dnd5e.wiki.controller.rest.model.xml;

import java.util.List;
import java.util.stream.Collectors;

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
	
	@XmlElement
	private String ability;
	
	@XmlElement
	private List<TraitVO> trait;
	
	public RaceVO(Race race) {
		name = StringUtils.capitalize(race.getName().toLowerCase());
		size = String.valueOf(race.getSize().name().charAt(0));
		speed = race.getSpeed();
		ability = race.getAbilityBonuses().stream().map(r -> r.getAbility().name() + r.getBonus()).collect(Collectors.joining(", "));
		trait = race.getFeatures().stream().map(TraitVO::new).collect(Collectors.toList());
	}
}
