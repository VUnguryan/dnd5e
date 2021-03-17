package com.dnd5e.wiki.model.hero;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
	
	@ElementCollection(targetClass = OptionType.class)
	@JoinTable(name = "option_types", joinColumns = @JoinColumn(name = "option_id"))
	@Column(name = "option_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private List<OptionType> optionTypes;
	
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
		ARCANE_SHOT("Магические выстрелы: Мистический Лучник (Воин)", "МВ"),
		MANEUVER("Маневры: Мастер боевых искуств (Воин)", "М МБИ"),
		METAMAGIC("Метамагия: Чародей", "ММ"),
		ELDRITCH_INVOCATION("Таинственные воззвания: Колдун","ТВ"),
		FIGHTING_STYLE("Боевые стили: Воин", "БС В"),
		ELEMENTAL_DISCIPLINE("Стихийные практики: Путь четырех стихий (Монах)", "СП ПЧС"),
		ARTIFICER_INFUSION("Инфузии: Изобретатель","И И"),
		RUNE("Руны: Рунический рыцарь", "РР, Р"),
		BONE("Договоры: Колдун", "Д К"),
		FIGHTING_STYLE_RANGER("Боевые стили: Следопыт", "БС С"), 
		FIGHTING_STYLE_PALADIN("Боевые стили: Паладин", "БС П"),
		FIGHTING_STYLE_BARD("Боевые стили: Колллегия Мечей, Бард", "БС Б"),
		FIGHTING_STYLE_BLOODHANTER("Боевые стили: Кровавый охотник", "БС КО"),
		BLOOD_CURSE("Проклятья крови: Кровавый Охотник", "ПК"),
		MUTAGEN("Мутагены: Ордена мутантов, (Кровавый Охотник)", "М ОМ"),
		WILD_SHAPE("Формы Дикого Облика: Друид", "ДО"), 
		PHILOSOPHICAL_SCHOOL("Философские школы: Философ Академии, Волшебник","ФШ ФА");

		private String name;
		private String shortName;
		public static OptionType parse(String type) {
			return Arrays.asList(values()).stream()
					.filter(t -> t.name.equals(type))
					.findFirst()
					.orElseThrow(IllegalArgumentException::new);
		}

		public String getDisplayName() {
			return name.substring(0, name.indexOf(":"));
		}
	}
}