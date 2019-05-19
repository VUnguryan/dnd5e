package com.dnd5e.wiki.model.creature;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.util.StringUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "skills")
public class Skill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Enumerated(EnumType.ORDINAL)
	private SkillType type;
	private byte bonus;

	public String getText() {
		return type != null ? String.format("%s %+d", StringUtils.capitalize(type.name().toLowerCase()), bonus) : "";
	}

	public String getCyrilicText() {
		return type != null
				? String.format("%s %+d", StringUtils.capitalize(type.getCyrilicName().toLowerCase()), bonus)
				: "";
	}
}