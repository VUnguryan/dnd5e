package com.dnd5e.wiki.model.travel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "transports")
public class Transport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	
	private TransportType type;
	private int cost;
	private Integer speedEncounter;
	private Integer speed;
	private Integer loadcapacity;
	private Integer weight;
	// экипаж
	private Integer crew;
	@Column(nullable = true)
	private Integer passengers;
	private Byte AC;
	private Short hp;
	@Column(nullable = true)
	private Short damageThreshold;
}