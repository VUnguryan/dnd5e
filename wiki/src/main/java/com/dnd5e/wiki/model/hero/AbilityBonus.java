package com.dnd5e.wiki.model.hero;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dnd5e.wiki.model.AbilityType;

import lombok.Data;

@Entity
@Table(name = "race_bonuses")
@Data
public class AbilityBonus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	private AbilityType ability;
	private byte bonus;
	
}