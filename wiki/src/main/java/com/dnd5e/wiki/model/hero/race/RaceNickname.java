package com.dnd5e.wiki.model.hero.race;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "race_nicknames")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RaceNickname {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@ManyToOne
	@JoinColumn(name = "race_id")
	private Race race;
	@Enumerated(EnumType.STRING)
	NicknameType type;
	
	@AllArgsConstructor
	@Getter
	public static enum NicknameType {
		CLAN("из клана", "Кланы:"),
		SURNAME("", "Фамилии:"),
		NICKNAME("по прозвищу", "Прозвища:"),
		HOUSE("из дома", "Дома:"),
		SQUAD("из отряда", "Отряды:"),
		TRIBE("из племени", "Племена:"),
		FROM("из", "По месту рождения:"),
		DYNASTY("династии", "Династии"),
		;

		private String name;
		private String display;
	}
}