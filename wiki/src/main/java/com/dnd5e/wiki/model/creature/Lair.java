package com.dnd5e.wiki.model.creature;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "lairs")
public class Lair {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int lairId;
	@Column(columnDefinition = "TEXT")
	private String description;
	@Column(columnDefinition = "TEXT")
	private String action;
	@Column(columnDefinition = "TEXT")
	private String effect;
}