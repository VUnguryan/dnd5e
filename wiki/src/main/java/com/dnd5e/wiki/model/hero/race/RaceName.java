package com.dnd5e.wiki.model.hero.race;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "race_names")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RaceName {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@Enumerated(EnumType.STRING)
	private  Sex sex;
	@ManyToOne
	@JoinColumn(name = "race_id")
	private Race race;
}
