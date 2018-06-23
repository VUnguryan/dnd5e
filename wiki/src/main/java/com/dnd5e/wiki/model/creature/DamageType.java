package com.dnd5e.wiki.model.creature;

public enum DamageType {
	FAIR("огонь"),
	COLD("холод"),
	LIGHTNING("электричество"),
	POISON("яд"),
	ACID("кислота"),
	SOUND("звук"),
	NECTOTIC("некротическая энергия"),
	
	CRUSHING("дробящий"),
	PIERCING ("колющий"),
	CHOPPING ("рубящий"),
	;
	private String cyrilicName;

	DamageType(String cyrilicName){
		this.cyrilicName = cyrilicName;
	}
	public String getCyrilicName() {
		return cyrilicName;
	}
}
