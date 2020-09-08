package com.dnd5e.wiki.model.creature;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Alignment {
	LAWFUL_GOOD("законно-добрый", "ЗД"), //0 
	LAWFUL_NEUTRAL("законно-нейтральный", "ЗН"), // 1
	LAWFUL_EVIL("законно-злой", "ЗЗ"), //2 
	TRUE_NEUTRAL("законно-нейтральный", "ЗН"), //3
	NEUTRAL_GOOD("нейтрально-добрый", "НД"), // 4
	NEUTRAL_EVIL("нейтрально-злой", "НЗ"), //5 
	CHAOTIC_GOOD("хаотично-добрый", "ХД"), //6
	CHAOTIC_NEUTRAL("хаотично-нейтральный", "ХН"), //7
	CHAOTIC_EVIL("хаотично-злой", "ХЗ"), // 8
	NEUTRAL("нейтральный", "Н"), //9
	WITHOUT("без мировоззрения", ""), // 10
	ALL_EVIL("любое злое мировоззрение", ""), // 11
	ALL("любое мировоззрение", "") // 12
	;

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