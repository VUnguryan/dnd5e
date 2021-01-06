package com.dnd5e.wiki.model.tavern;

public class HeroUtil {
	private static int[] exp = { 0, 300, 900, 2_700, 6_500, 14_000, 23_000, 34_000, 48_000, 64_000, 85_000, 100_000,
			120_000, 140_000, 165_000, 195_000, 225_000, 265_000, 305_000, 355_000 };

	public static int getLevel(int experience) {
		for (int i = 0; i < exp.length; i++) {
			if (experience < exp[i]) {
				return i;
			}
		}
		return 20;
	}

	public static int getMastery(int level) {
		if (level < 5)
			return 2;
		else if (level < 9)
			return 3;
		else if (level < 13)
			return 4;
		else if (level < 17)
			return 5;
		return 6;
	}

	public static int getModifier(int value) {
		if (value == 1)
			return -5;
		else if (value <= 3)
			return -4;
		else if (value <= 5)
			return -3;
		else if (value <= 7)
			return -2;
		else if (value <= 9)
			return -1;
		else if (value <= 11)
			return 0;
		else if (value <= 13)
			return 1;
		else if (value <= 15)
			return 2;
		else if (value <= 17)
			return 3;
		else if (value <= 19)
			return 4;
		else if (value <= 21)
			return 5;
		else if (value <= 23)
			return 6;
		else if (value <= 25)
			return 7;
		else if (value <= 27)
			return 8;
		else if (value <= 29)
			return 9;
		return 10;
	}

	public static int getStartLevelExp(int level) {
		return exp[level - 1];
	}
}