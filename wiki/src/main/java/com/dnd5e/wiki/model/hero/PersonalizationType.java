package com.dnd5e.wiki.model.hero;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PersonalizationType {
	TRAIT("Черта характера"),
	IDEAL("Идеал"),
	AFFECTION("Привязанность"),
	WEAKNESS("Слабость"),
	LIFE_IN_SECLUSION("Жизнь в уединении"),
	EMPLOYMENT("Занятие"),
	SPECIALIZATION("Специализация"),
	ADOPTED_CULTURE("Перенятая культура"),
	VALUABLE_ITEMS("Ценные предметы");
	private String name;
}