package com.dnd5e.wiki.model.hero.classes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dnd5e.wiki.model.hero.AbilityBonus;

import lombok.Data;

@Entity
@Table(name = "race_features")
@Data
public class Feature {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@OneToMany
	@JoinColumn(name = "feature_id")
	private List<AbilityBonus> abilityBonuses;
}
