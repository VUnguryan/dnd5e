package com.dnd5e.wiki.model.hero.classes;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dnd5e.wiki.model.Rest;

import lombok.Data;

@Entity
@Table(name = "counters")
@Data
public class Counter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@Enumerated(EnumType.STRING)
	private CounterType type;
	private int value;
	@Enumerated(EnumType.STRING)
	private Rest restType;
}
