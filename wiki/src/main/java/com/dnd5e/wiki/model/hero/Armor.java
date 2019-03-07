package com.dnd5e.wiki.model.hero;

import javax.persistence.Column;
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
	private float weight;
	
	@Column(nullable = true)
	private Integer forceRequirements;
	private boolean stelsHindrance;
	private int AC;
	
	@Enumerated(EnumType.ORDINAL)
	private ArmorType type;
	private String description;
}
