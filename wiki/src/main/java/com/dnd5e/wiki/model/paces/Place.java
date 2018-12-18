package com.dnd5e.wiki.model.paces;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "places")
@Data
public class Place {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "parent_id")
	private Place parent;
	
	@OneToMany(mappedBy = "parent",  orphanRemoval=true)
	private List<Place> children;
}