package com.dnd5e.wiki.model.creature;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DamageType {
	FAIR("огонь"),
	COLD("холод"),
	LIGHTNING("электричество"),
	POISON("яд"),
	ACID("кислота"),
	SOUND("звук"),
	NECTOTIC("некротическая энергия"),
	PSYCHIC("психическая энергия"),
	
	CRUSHING("дробящий"),
	PIERCING ("колющий"),
	CHOPPING ("рубящий"),
	PHYSICAL("дробящий, колющий и рубящий урон от немагических атак"),
	NO_NOSILVER("дробящий, колющий и рубящий урон от немагических атак, а также от немагического оружия, которое при этом не посеребрено"),
	NO_DAMAGE("без урона");
	
	private String cyrilicName;

	public static DamageType parse(String damageTypeString) {
		for (DamageType damageType : values()) {
			if (damageType.cyrilicName.equals(damageTypeString)) {
				return damageType;
			}
		}
		return null;
	}
}
