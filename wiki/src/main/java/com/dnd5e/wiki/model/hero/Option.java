package com.dnd5e.wiki.model.hero;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dnd5e.wiki.model.Book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "options")
public class Option {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String englishName;
	
	@Enumerated(EnumType.STRING)
	private OptionType optionType;

	private String prerequisite;
	private Integer level;

	@Column(columnDefinition = "TEXT")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "source")
	private Book book;
	private Short page;

	@AllArgsConstructor
	@Getter
	public enum OptionType {
		ARCANE_SHOT("Магический выстрел, Мистический Лучник, Воин", "МВ"),
		MANEUVER("Маневр, Мастер боевых искуств, Воин", "М, МБИ"),
		METAMAGIC("Метамагия, Чародей", "ММ"),
		ELDRITCH_INVOCATION("Таинственное воззвание, Колдун","ТВ"),
		FIGHTING_STYLE("Боевой стиль, Воин", "БС"),
		ELEMENTAL_DISCIPLINE("Стихийные практики, Путь четырех стихий, Монах", "СП, ПЧС"),
		ARTIFICER_INFUSION("Инфузия, Изобретатель","И, И"),
		RUNE("Руна, Рунический рыцарь", "РР, Р"),
		BONE("Договор, Колдун", "Д, К"),;

		private String name;
		private String shortName;
		public static OptionType parse(String type) {
			return Arrays.asList(values()).stream().filter(t -> t.name.equals(type)).findFirst().orElseThrow(IllegalArgumentException::new);
		}
	}
}