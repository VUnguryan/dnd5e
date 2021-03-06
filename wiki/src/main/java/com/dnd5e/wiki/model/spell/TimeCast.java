package com.dnd5e.wiki.model.spell;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name ="spell_times")
public class TimeCast {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private int number;
	@Enumerated(EnumType.STRING)
	private TimeUnit unit;
	private String condition;
}