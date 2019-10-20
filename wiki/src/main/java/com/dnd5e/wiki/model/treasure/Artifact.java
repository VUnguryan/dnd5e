package com.dnd5e.wiki.model.treasure;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.hero.classes.HeroClass;

import lombok.Data;

@Entity
@Table(name = "artifactes")
@Data
public class Artifact {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	@ManyToOne
	@JoinColumn(name = "source")
	private Book book;
	
	@OneToMany
	private List<HeroClass> custClasses;
	
	public int getCost() {
		if (cost != null) {
			return cost;
		}
		return consumed ? rarity.getBaseCost() / 2 : rarity.getBaseCost();
	}
}