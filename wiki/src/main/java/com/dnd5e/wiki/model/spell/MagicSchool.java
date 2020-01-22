package com.dnd5e.wiki.model.spell;

public enum MagicSchool {
	CONJURATION("воплощение"), // 0
	EVOCATION("вызов"),        // 1
	ILLUSION("иллюзия"),       // 2
	NECROMANCY("некромантия"), // 3
	ABJURATION("ограждение"),  // 4
	ENCHANTMENT("очарование"), //5
	TRANSMUTATION("преобразование"), // 6
	DIVINATION("прорицание"); // 7

	private String name;

	private MagicSchool(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static MagicSchool getMagicSchool(String name) {
		for (MagicSchool school : values()) {
			if (name.equalsIgnoreCase(name)) {
				return school;
			}
		}
		return null;
	}
}