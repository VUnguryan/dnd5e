package com.dnd5e.wiki.controller.rest.model.json.etools;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Skill {
	@JsonProperty("arcana")
	private String arcana;
	@JsonProperty("acrobatics")
	private String acrobatics;
	@JsonProperty("athletics")
	private String athletics;
	@JsonProperty("perception")
	private String perception;
	@JsonProperty("history")
	private String history;
	@JsonProperty("medicine")
	private String medicine;
	@JsonProperty("religion")
	private String religion;
	@JsonProperty("stealth")
	private String stealth;
	@JsonProperty("deception")
	private String deception;
	@JsonProperty("insight")
	private String insight;
	@JsonProperty("intimidation")
	private String intimidation;
	@JsonProperty("investigation")
	private String investigation;
	@JsonProperty("nature")
	private String nature;
	@JsonProperty("survival")
	private String survival;
	@JsonProperty("performance")
	private String performance;
	@JsonProperty("persuasion")
	private String persuasion;
	@JsonProperty("animal handling")
	private String animalHandling;
	@JsonProperty("sleight of hand")
	private String sleightOfHand;

	public Skill(List<com.dnd5e.wiki.model.creature.Skill> skills) {
		for (com.dnd5e.wiki.model.creature.Skill skill : skills) {
			if (skill.getType() == null) {
				continue;
			}
			switch (skill.getType()) {
			case PERCEPTION:
				perception = String.format("%+d", skill.getBonus());
				break;
			case HISTORY:
				history = String.format("%+d", skill.getBonus());
				break;
			case MEDICINE:
				medicine = String.format("%+d", skill.getBonus());
				break;
			case RELIGION:
				religion = String.format("%+d", skill.getBonus());
				break;
			case STEALTH:
				stealth = String.format("%+d", skill.getBonus());
				break;
			case ARCANA:
				arcana = String.format("%+d", skill.getBonus());
				break;
			case DECEPTION:
				deception = String.format("%+d", skill.getBonus());
				break;
			case INSIGHT:
				insight = String.format("%+d", skill.getBonus());
				break;
			case ACROBATICS:
				acrobatics = String.format("%+d", skill.getBonus());
				break;
			case ATHLETICS:
				athletics = String.format("%+d", skill.getBonus());
				break;
			case INTIMIDATION:
				intimidation = String.format("%+d", skill.getBonus());
				break;
			case INVESTIGATION:
				investigation = String.format("%+d", skill.getBonus());
				break;
			case NATURE:
				nature = String.format("%+d", skill.getBonus());
				break;
			case SURVIVAL:
				survival = String.format("%+d", skill.getBonus());
				break;
			case PERFORMANCE:
				performance = String.format("%+d", skill.getBonus());
				break;
			case PERSUASION:
				persuasion = String.format("%+d", skill.getBonus());
				break;
			case ANIMAL_HANDLING:
				animalHandling = String.format("%+d", skill.getBonus());
				break; 
			case SLEIGHT_OF_HAND:
				sleightOfHand = String.format("%+d", skill.getBonus());
				break; 
			default:
				break;
			}
		}
	}
}