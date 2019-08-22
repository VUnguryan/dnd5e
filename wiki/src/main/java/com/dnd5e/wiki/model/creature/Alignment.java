package com.dnd5e.wiki.model.creature;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Alignment {
	LAWFUL_GOOD("законно-добрый", "ЗД"),
	LAWFUL_NEUTRAL("законно-нейтральный", "ЗН"),
	LAWFUL_EVIL("законно-злой", "ЗЗ"),
	TRUE_NEUTRAL("законно-нейтральный", "ЗН"),
	NEUTRAL_GOOD("нейтрально-добрый", "НД"),
	NEUTRAL_EVIL("нейтрально-злой", "НЗ"), 
	CHAOTIC_GOOD("хаотично-добрый", "ХД"),
	CHAOTIC_NEUTRAL("хаотично-нейтральный", "ХН"),
	CHAOTIC_EVIL("хаотично-злой", "ХЗ"),
	NEUTRAL("нейтральный", "Н"),
	WITHOUT("без мировоззрения", "");

	private String cyrilicName;
	private String shortName;
	
	public static Alignment parse(String alignment) {
		if (alignment.equals("нейтральный")) {
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