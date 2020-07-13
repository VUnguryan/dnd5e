package com.dnd5e.wiki.controller.rest.model.json.shaped;

import com.dnd5e.wiki.model.spell.Spell;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class Components {
	private Boolean verbal;
	private Boolean somatic;
	private Boolean material;
	private String materialMaterial;
	public Components(Spell spell) {
		if (spell.getVerbalComponent()) {
			verbal = true;
		}
		if (spell.getSomaticComponent()) {
			somatic = true;
		}
		if (spell.getMaterialComponent()) {
			material = true;
		}
		if (spell.getAdditionalMaterialComponent() != null) {
			materialMaterial = spell.getAdditionalMaterialComponent();
		}
	}
}