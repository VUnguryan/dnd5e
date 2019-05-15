package com.dnd5e.wiki.model.stock;

public enum Currency {
	MM("мм", 1f),
	SM("см", 10f),
	EM("эм", 50f),
	GM("зм", 100f),
	PM("пм", 1000f);
	
	private String name;
	private float coef;
	
	Currency(String name, float coef) {
		this.name = name;
		this.coef = coef;
	}

	public String getName() {
		return name;
	}

	public float convert(Currency currency, int cost) {
		return currency.coef / this.coef * cost;
	}
}