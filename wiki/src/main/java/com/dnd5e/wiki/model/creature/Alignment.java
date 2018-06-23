package com.dnd5e.wiki.model.creature;

public enum Alignment {
	LAWFUL_GOOD("законно-добрый"),
	LAWFUL_NEUTRAL("законно-нейтральный"), LAWFUL_EVIL("законно-злой"), TRUE_NEUTRAL(
			"законно-нейтральный"), NEUTRAL_GOOD("нейтрально-добрый"), NEUTRAL_EVIL("нейтрально-злой"), CHAOTIC_GOOD(
					"хаотично-добрый"), CHAOTIC_NEUTRAL(
							"хаотично-нейтральный"), CHAOTIC_EVIL("хаотично-злой"),
	NEUTRAL("нейтральный"),
	WITHOUT("без мировоззрения");

	private String cyrilicName;

	Alignment(String cyrilicName) {
		this.cyrilicName = cyrilicName;
	}

	public String getCyrilicName() {
		return cyrilicName;
	}

	public static Alignment parse(String alignment) {
		if (!alignment.equals("нейтральный")) {
			return NEUTRAL;
		}
		if (alignment.contains("-зл")) {
			if (alignment.contains("хаот")) {
				return Alignment.CHAOTIC_EVIL;
			}
			if (alignment.contains("закон")) {
				return LAWFUL_EVIL;
			}
			if (alignment.contains("нейтр")) {
				return NEUTRAL_EVIL;
			}
		} else if (alignment.contains("-добр")) {
			if (alignment.contains("нейтр")) {
				return Alignment.NEUTRAL_GOOD;
			}
			if (alignment.contains("хаот")) {
				return Alignment.CHAOTIC_GOOD;
			}
			if (alignment.contains("закон")) {
				return Alignment.LAWFUL_GOOD;
			}
		} else if (alignment.contains("-нейтр")) {
			if (alignment.contains("закон")) {
				return Alignment.LAWFUL_NEUTRAL;
			}
			if (alignment.contains("хаот")) {
				return Alignment.CHAOTIC_NEUTRAL;
			}
		}
		return WITHOUT;
	}
}
