package com.dnd5e.wiki.model.tavern;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dnd5e.wiki.model.creature.HabitatType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "taverna_drinks")
@Getter
@Setter
public class TavernaDrink {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@Enumerated(EnumType.STRING)
	private HabitatType habitat;
	@Enumerated(EnumType.STRING)
	private TavernaCategory category;
}