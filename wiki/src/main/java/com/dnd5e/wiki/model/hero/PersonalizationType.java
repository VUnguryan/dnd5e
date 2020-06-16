package com.dnd5e.wiki.model.hero;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PersonalizationType {
	LIFE_IN_SECLUSION("Жизнь в уединении"),
	EMPLOYMENT("Занятие"),
	SPECIALIZATION("Специализация"),
	ADOPTED_CULTURE("Перенятая культура"),
	VALUABLE_ITEMS("Ценные предметы"),
	SCAM("Афера"), 
	AMPLOIS("Амплуа"),
	DEFINING_EVENT("Определяющее событие"),
	TRAIT("Черта характера"),
	IDEAL("Идеал"),
	AFFECTION("Привязанность"),
	WEAKNESS("Слабость"), 
	WHY_ARE_YOU_HERE("Зачем вы здесь?"),
	WHERE_ARE_YOU_FROM("Откуда вы?"),
	HERITAGE("Наследие"), 
	DISFIGURED("Обезображенный"), 
	CREED("Убеждение");

	private String name;
}