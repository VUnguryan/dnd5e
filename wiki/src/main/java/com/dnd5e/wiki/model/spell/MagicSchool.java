package com.dnd5e.wiki.model.spell;

public enum MagicSchool {
	EMBODIMENTS("воплощение"),
	INVOCATION("вызов"),
	ILLUSION("иллюзия"),
	NECROMANCY("некромантия"),
	PROTECTION("ограждение"),
	CHARM("очарование"),
	TRANSFORMATION("преобразование"),
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