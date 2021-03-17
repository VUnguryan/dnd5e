package com.dnd5e.wiki.model.creature;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Dice {
	d4(4),
	d6(6),
	d8(8),
	d10(10),
	d12(12),
	d20(20),
	d100(100),
	d3(3),
	d2(2);
	
	private int maxValue;
	
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