package com.dnd5e.wiki.model.tavern;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dnd5e.wiki.model.creature.HabitatType;
import com.dnd5e.wiki.model.creature.SkillType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "name")
@Entity
@Table(name = "taverna_visitors")
public class Visitor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;

	@OneToMany (targetEntity = VisitorChance.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "visitor_id")
	private List<VisitorChance> chance;
	
	@ElementCollection(targetClass = SkillType.class)
	@CollectionTable(name = "taverna_visitor_habitates")
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	List<HabitatType> habitatTypes;
}