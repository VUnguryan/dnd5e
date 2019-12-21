package com.dnd5e.wiki.model.creature;

import java.util.EnumSet;
import java.util.Set;

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
	
	BLUDGEONING("дробящий"),
	PIERCING ("колющий"),
	SLASHING ("рубящий"),
	PHYSICAL("дробящий, колющий и рубящий урон от немагических атак"),
	NO_NOSILVER("дробящий, колющий и рубящий урон от немагических атак, а также от немагического оружия, которое при этом не посеребрено"),
	NO_DAMAGE("без урона"), 
	RADIANT("излучение"),
	NO_ADMANTIT("дробящий, колющий и рубящий урон от немагических атак, а также от немагического оружия, которое при этом не изготовлено из адамантина"), 
	PHYSICAL_MAGIC("дробящий, колющий и рубящий урон от магического оружия");

	private String cyrilicName;

	public static DamageType parse(String damageTypeString) {
		for (DamageType damageType : values()) {
			if (damageType.cyrilicName.equals(damageTypeString)) {
				return damageType;
			}
		}
		return null;
	}

	public static Set<DamageType> getVulnerability()
	{
		return EnumSet.of(BLUDGEONING, PIERCING, SLASHING, FAIR, COLD, LIGHTNING, POISON, ACID, SOUND, NECTOTIC, PSYCHIC, RADIANT);
	}

	public static Set<DamageType> getResistance()
	{
		return EnumSet.of(BLUDGEONING, PIERCING, SLASHING, FAIR, COLD, LIGHTNING, POISON, ACID, SOUND, NECTOTIC,
				PSYCHIC, RADIANT, PHYSICAL, NO_NOSILVER, NO_ADMANTIT, PHYSICAL_MAGIC);
	}
}