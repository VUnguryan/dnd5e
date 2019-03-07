package com.dnd5e.wiki.model.hero;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dnd5e.wiki.model.creature.DamageType;
import com.dnd5e.wiki.model.creature.Dice;

import lombok.Data;

@Entity
@Table(name = "weapons")
@Data
public class Weapon {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private int cost;
	@Enumerated(EnumType.ORDINAL)
	private Currency currency;
	private float weight;
	
	@Enumerated(EnumType.ORDINAL)
	private Dice damageDice;
	private byte numberDice;
	@Enumerated(EnumType.ORDINAL)
	private DamageType damageType;
	@Enumerated(EnumType.ORDINAL)
	private WeaponType type;
	
	@ManyToMany
	List<WeaponProperty> properties;
	
	private String description;
}
