package com.dnd5e.wiki.model.creature;

public enum Dice {
	d4,
	d6,
	d8,
	d10,
	d12,
	d20,
	d100;

	public static Dice parse(int dice) {
		switch(dice) {
			case 4:
				return d4;
			case 6:
				return d6;
			case 8:
				return d8;
			case 10:
				return d10;
			case 12:
				return d12;
			case 20:
				return d20;
			case 100:
				return d100;
		}
		
		return null;
	}
	public String getName() {
		return name();
	}
}
