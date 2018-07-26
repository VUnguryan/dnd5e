package com.dnd5e.wiki.util;

public final class ChallengeRating {
	private static int[] sections = { 10, 25, 50, 100, 200, 450, 700, 1100, 1800, 2300, 2900, 3900, 5000, 5900, 7200,
			8400, 10000, 11500, 18000, 20000, 22000, 25000};
	private static String[] crs = { "1/8", "1/4", "1/2" };

	private ChallengeRating() {
	};

	public static String getCR(int exp) {
		String expStr = "0";
		if ((exp > 10) && (exp <= 25)) {
			expStr = "1/8";
		} else if ((exp > 25) && (exp <= 50)) {
			expStr = "1/8";
		} else if ((exp > 50) && (exp <= 100)) {
			expStr = "1/4";
		} else if ((exp > 100) && (exp <= 200)) {
			expStr = "1/2";
		} else if ((exp > 200) && (exp <= 450)) {
			expStr = "1";
		} else if ((exp > 450) && (exp <= 700)) {
			expStr = "2";
		} else if ((exp > 700) && (exp <= 1100)) {
			expStr = "3";
		} else if ((exp > 1100) && (exp <= 1800)) {
			expStr = "4";
		} else if ((exp > 1800) && (exp <= 2300)) {
			expStr = "5";
		} else if ((exp > 2300) && (exp <= 2900)) {
			expStr = "6";
		} else if ((exp > 2900) && (exp <= 3900)) {
			expStr = "7";
		} else if ((exp > 3900) && (exp <= 5000)) {
			expStr = "8";
		} else if ((exp > 5000) && (exp <= 5900)) {
			expStr = "9";
		} else if ((exp > 5900) && (exp <= 7200)) {
			expStr = "10";
		} else if ((exp > 7200) && (exp <= 8400)) {
			expStr = "11";
		} else if ((exp > 8400) && (exp <= 10000)) {
			expStr = "12";
		} else if ((exp > 10000) && (exp <= 11500)) {
			expStr = "13";
		} else if ((exp > 11500) && (exp <= 13000)) {
			expStr = "14";
		} else if ((exp > 13000) && (exp <= 15000)) {
			expStr = "15";
		} else if ((exp > 15000) && (exp <= 18000)) {
			expStr = "16";
		} else if ((exp > 18000) && (exp <= 20000)) {
			expStr = "17";
		} else if ((exp > 20000) && (exp <= 22000)) {
			expStr = "18";
		} else if ((exp > 22000) && (exp <= 25000)) {
			expStr = "19";
		} else if ((exp > 25000) && (exp <= 33000)) {
			expStr = "20";
		} else if ((exp > 33000) && (exp <= 41000)) {
			expStr = "21";
		} else if ((exp > 41000) && (exp <= 50000)) {
			expStr = "22";
		} else if ((exp > 50000) && (exp <= 62000)) {
			expStr = "23";
		} else if ((exp > 62000) && (exp <= 75000)) {
			expStr = "24";
		} else if ((exp > 75000) && (exp <= 90000)) {
			expStr = "25";
		} else if ((exp > 90000) && (exp <= 105000)) {
			expStr = "26";
		} else if ((exp > 105000) && (exp <= 120000)) {
			expStr = "27";
		} else if ((exp > 120000) && (exp <= 135000)) {
			expStr = "28";
		} else if ((exp > 135000) && (exp <= 155000)) {
			expStr = "29";
		} else if (exp > 155000) {
			expStr = "30";
		}
		return expStr;
	}
}
