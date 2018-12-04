package com.dnd5e.wiki.model.hero;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dnd5e.wiki.model.creature.Ability;

import lombok.Data;

@Entity
@Table(name = "race_bonuses")
@Data
public class Bonus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Enumerated(EnumType.ORDINAL)
	private Ability ability;
	private byte bonus;
}
