package com.dnd5e.wiki.model.hero;

public enum ArmorType {
	LIGHT("Лёгкий доспех", "1 минута", "1 минута", "+ модификатор Лов"),
	MEDIUM("Средний доспех", "5 минут",	"1 минута", "+ модификатор Лов (макс. 2)"),
	HEAVY("Тяжёлый доспех", "10 минут", "5 минут"),
	SHIELD("Щит", "1 действие", "1 действие");

	private String name;
	private String dressingTime;
	private String removalTime;
	private String bonus ="";

	ArmorType(String name, String dressingTime, String removalTime) {
		this.name = name;
	}

	ArmorType(String name, String dressingTime, String removalTime, String bonus) {
		this(name, dressingTime, removalTime);
		this.bonus = bonus;
	}

	public String getName() {
		return name;
	}

	public String getDressingTime() {
		return dressingTime;
	}

	public String getRemovalTime() {
		return removalTime;
	}
	public String getBonus() {
		return bonus;
	}
}