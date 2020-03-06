package com.dnd5e.wiki.controller.rest.model.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.hero.AbilityBonus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ModifierVO {


	@XmlAttribute
	private String category;

	@XmlValue
	private String text;
	
	public ModifierVO(Type abilityScore, AbilityBonus bonus) {
		category = abilityScore.value;
		text = String.format("%s Score %+d",
				StringUtils.capitalize(bonus.getAbility().name().toLowerCase()),
				bonus.getBonus());
	}
	
	@AllArgsConstructor
	@Getter
	enum Type {
		BONUS("Bonus"),
		ABILITY_SCORE("Ability Score");
		private String value;
	}
}