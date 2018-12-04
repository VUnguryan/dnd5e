package com.dnd5e.wiki.model.artifact;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "artifactes")
@Data
public class Artifact {
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@Enumerated(EnumType.ORDINAL)
	private Rarity rarity;

	@Enumerated(EnumType.ORDINAL)
	private ArtifactType type;
	private Boolean customization;
	@Column(columnDefinition = "TEXT")
	private String description;
	private boolean consumed;
	@Column(nullable = true)
	private Integer cost;

	public int getCost() {
		if (cost != null) {
			return cost;
		}
		return consumed ? rarity.getBaseCost() / 2 : rarity.getBaseCost();
	}
}