package com.dnd5e.wiki.model.hero.classes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "classesFeatureLevelDefinitions")
public class FeatureLevelDefinition {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String prefix;
	private String sufix;
	private Byte l1;
	private Byte l2;
	private Byte l3;
	private Byte l4;
	private Byte l5;
	private Byte l6;
	private Byte l7;
	private Byte l8;
	private Byte l9;
	private Byte l10;
	private Byte l11;
	private Byte l12;
	private Byte l13;
	private Byte l14;
	private Byte l15;
	private Byte l16;
	private Byte l17;
	private Byte l18;
	private Byte l19;
	private Byte l20;

	public byte getByLevel(int level) {
		switch (level) {
		case 1:
			return l1;
		case 2:
			return l2;
		case 3:
			return l3;
		case 4:
			return l4;
		case 5:
			return l5;
		case 6:
			return l6;
		case 7:
			return l7;
		case 8:
			return l8;
		case 9:
			return l9;
		case 10:
			return l10;
		case 11:
			return l11;
		case 12:
			return l12;
		case 13:
			return l13;
		case 14:
			return l14;
		case 15:
			return l15;
		case 16:
			return l16;
		case 17:
			return l17;
		case 18:
			return l18;
		case 19:
			return l19;
		case 20:
			return l20;
		default:
			return -1;
		}
	}
}