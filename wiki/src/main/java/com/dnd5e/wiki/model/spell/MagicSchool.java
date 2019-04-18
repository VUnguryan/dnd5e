package com.dnd5e.wiki.model.spell;

public enum MagicSchool {
	CONJURATION("воплощение"),
	EVOCATION("вызов"),
	ILLUSION("иллюзия"),
	NECROMANCY("некромантия"),
	ABJURATION("ограждение"),
	ENCHANTMENT("очарование"),
	TRANSMUTATION("преобразование"),
	DIVINATION("прорицание");

	private String name;

	private MagicSchool(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static MagicSchool getMagicSchool(String name) {
		for (MagicSchool school : values()) {
			if (name.equals(name)) {
				return school;
			}
		}
		return null;
	}
}