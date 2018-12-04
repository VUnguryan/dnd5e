package com.dnd5e.wiki.model.hero;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "armors")
@Data
public class Armor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private int cost;
	@Enumerated(EnumType.ORDINAL)
	private Currency currency;
	private float weight;
	
	private int AC;
}
