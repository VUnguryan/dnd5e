package com.dnd5e.wiki.model.stock;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dnd5e.wiki.model.Book;

import lombok.Data;

@Entity
@Table(name = "equipments")
@Data
public class Equipment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private int cost;
	
	@Enumerated(EnumType.ORDINAL)
	private Currency currency;
	
	private float weight;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@Enumerated(EnumType.STRING)
	private EquipmentType type;
	
	@ManyToOne
	@JoinColumn(name = "source")
	private Book book;
}
