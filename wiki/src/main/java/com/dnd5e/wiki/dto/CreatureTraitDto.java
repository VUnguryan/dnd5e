package com.dnd5e.wiki.dto;

import com.dnd5e.wiki.model.creature.CreatureTrait;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatureTraitDto {
	private String name;
	private String description;
	private boolean reverseIndent = false;

	public CreatureTraitDto(CreatureTrait trait) {
		name = trait.getName();
		description = trait.getDescription();
		if (name.trim().startsWith("Колдовство") || name.trim().startsWith("Использование заклинаний")
				|| name.trim().startsWith("Врождённое колдовство")) {
			reverseIndent = true;
		}
	}
}