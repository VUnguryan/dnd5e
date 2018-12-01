package com.dnd5e.wiki.model.hero;

public enum Currency {
	MM("мм", 1),
	SM("см", 10),
	EM("эм", 50),
	GM("зм",100),
	PM("пм", 1000);
	
	private String name;
	private int coef;
	
	Currency(String name, int coef) {
		this.name = name;
		this.coef = coef;
	}

	public String getName() {
		return name;
	}

	public int convert(Currency currency, int cost) {
		return currency.coef / this.coef * cost;
	}
}
