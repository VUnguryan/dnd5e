package com.dnd5e.wiki.model.stock;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name = "armors")
@Getter
public class Armor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String englishName;
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