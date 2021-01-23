package com.dnd5e.wiki.model.stock;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dnd5e.wiki.model.Book;
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
	private String englishName;
	private int cost;
	
	@Enumerated(EnumType.ORDINAL)
	private Currency currency;
	
	private float weight;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = true)
	private Dice damageDice;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = true)
	private Dice twoHandDamageDice;
	
	@Column(nullable = true)
	private Byte numberDice;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = true)
	private DamageType damageType;
	
	@Enumerated(EnumType.ORDINAL)
	private WeaponType type;
	
	@Column(nullable = true)
	private Short minDistance;

	@Column(nullable = true)
	private Short maxDistance;

	@ManyToMany
	List<WeaponProperty> properties;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@Column(columnDefinition = "TEXT")
	private String special;
	
	@ManyToOne
	@JoinColumn(name = "source")
	private Book book;
	
	public String getDamage() {
		if (numberDice == null) {
			return String.format("%d", damageDice);
			
		}
		return String.format("%d%d", numberDice, damageDice);
	}
}