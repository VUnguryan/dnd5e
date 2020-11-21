package com.dnd5e.wiki.controller.rest.model.json.shaped;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.controller.rest.model.xml.Compendium;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class Spell {
	private String name;
	private byte level;
	private String school;
	private Boolean ritual;
	@JsonProperty("components")
	private Components components;

	private String castingTime;
	private String range;
	private String duration;

	private String description;
	private Boolean concentration;

	private String higherLevel;
	@JsonProperty("save")
	private Save save;
	@JsonProperty("attack")
	private Attack attack;
	@JsonProperty("heal")
	private Heal heal;

	public Spell(com.dnd5e.wiki.model.spell.Spell spell) {
		name = StringUtils.capitalize(spell.getName().toLowerCase());
		level = spell.getLevel();
		school = StringUtils.capitalize(spell.getSchool().name().toLowerCase());
		if (spell.getRitual() != null && spell.getRitual()) {
			ritual = true;
		}
		castingTime = spell.getTimeCast();
		range = spell.getDistance();
		components = new Components(spell);
		duration = spell.getDuration();
		concentration = spell.getConcentration() ? spell.getConcentration() : null;
		description = Compendium.removeHtml(spell.getDescription().replace("</p>", " "));
		higherLevel = spell.getUpperLevel() == null ? null : Compendium.removeHtml(spell.getUpperLevel());

		if (description.contains("атаку заклинанием")) {
			attack = new Attack(spell, description);
		} else if (description.contains("спасброс")) {
			Save s = new Save(spell, description);
			if (s.getAbility() != null) {
				save = new Save(spell, description);
			}
		}
		if (description.contains("восстанавливает количество хитов")
				|| description.contains("восстанавливают хиты в количестве")
				|| description.contains("Цель восстанавливает ")) {
			heal = new Heal(spell, description);
		}
		if (spell.getEnglishName().equalsIgnoreCase("heal")) {
			heal = new Heal(70);
		}
	}
}