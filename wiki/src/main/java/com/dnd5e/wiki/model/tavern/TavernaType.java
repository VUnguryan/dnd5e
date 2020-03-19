package com.dnd5e.wiki.model.tavern;

import java.util.Random;

public enum TavernaType {
	BEER("Пивная", "Кабак"), 
	INN("Постоялый двор", "Трактир", "Таверна"), 
	HOTEL("Гостиница", "Отель");
	
	private final Random rnd = new Random();

	private String[] names;
	TavernaType(String... names){
		this.names = names;
	}
	public String getName() {
		return names[rnd.nextInt(names.length)];
	}
	public String getNames() {
		return String.join(", ", names);
	}
}