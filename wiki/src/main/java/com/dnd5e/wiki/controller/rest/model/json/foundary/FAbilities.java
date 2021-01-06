package com.dnd5e.wiki.controller.rest.model.json.foundary;

import com.dnd5e.wiki.model.AbilityType;
import com.dnd5e.wiki.model.creature.Creature;
import com.dnd5e.wiki.model.creature.SavingThrow;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FAbilities {
	private FAbility str;
	private FAbility dex;
	private FAbility con;
    @JsonProperty("int")
	private FAbility intel;
	private FAbility wis;
	private FAbility cha;
	public FAbilities(Creature creature) {
		FAbility str = new FAbility();
		str.setValue(creature.getStrength());
		str.setSave(getSave(creature, AbilityType.STRENGTH));
		str.setMod(AbilityType.getModifier(str.getValue()));
		this.str = str;
		
		FAbility dex = new FAbility();
		dex.setValue(creature.getDexterity());
		dex.setSave(getSave(creature, AbilityType.DEXTERITY));
		if (dex.getSave() > 0) {
			dex.setProficient((byte) 1);
		}
		dex.setMod(AbilityType.getModifier(dex.getValue()));
		this.dex = dex;
		
		FAbility con = new FAbility();
		con.setValue(creature.getConstitution());
		con.setSave(getSave(creature, AbilityType.CONSTITUTION));
		if (con.getSave() > 0) {
			con.setProficient((byte) 1);
		}
		con.setMod(AbilityType.getModifier(con.getValue()));
		this.con = con;
		
		FAbility intel = new FAbility();
		intel.setValue(creature.getIntellect());
		intel.setSave(getSave(creature, AbilityType.INTELLIGENCE));
		if (intel.getSave() > 0) {
			intel.setProficient((byte) 1);
		}
		intel.setMod(AbilityType.getModifier(intel.getValue()));
		this.intel = intel;
		
		FAbility wis = new FAbility();
		wis.setValue(creature.getWizdom());
		wis.setSave(getSave(creature, AbilityType.WISDOM));
		if (wis.getSave() > 0) {
			wis.setProficient((byte) 1);
		}
		wis.setMod(AbilityType.getModifier(wis.getValue()));
		this.wis = wis;
		
		FAbility cha = new FAbility();
		cha.setValue(creature.getCharisma());
		cha.setSave(getSave(creature, AbilityType.CHARISMA));
		if (cha.getSave() > 0) {
			cha.setProficient((byte) 1);
		}
		cha.setMod(AbilityType.getModifier(cha.getValue()));
		this.cha = cha;
	}
	
	private byte getSave(Creature creature, AbilityType type) {
		return creature.getSavingThrows().stream()
				.filter(st -> st.getAbility().equals(type))
				.map(SavingThrow::getBonus)
				.findFirst()
				.orElse((byte) 0);
	}
}