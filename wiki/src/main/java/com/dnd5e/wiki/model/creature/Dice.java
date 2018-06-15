package com.dnd5e.wiki.model.creature;

public enum Dice {
	D4, D6, D8, D10, D12, D20, D100;

	public static Dice parse(int dice) {
		switch(dice) {
			case 4:
				return D4;
			case 6:
				return D6;
			case 8:
				return D8;
			case 10:
				return D10;
			case 12:
				return D12;
			case 20:
				return D20;
			case 100:
				return D100;
		}
		return null;
	}
}
