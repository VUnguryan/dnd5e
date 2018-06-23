package com.dnd5e.wiki.model.creature;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "skills")
@Data
public class Skill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Enumerated(EnumType.ORDINAL)
	private SkillType type;
	private byte bonus;
	
	public String getText() {
		return String.format("%s %+d ", type.getCyrilicName(), bonus);
	}
}
