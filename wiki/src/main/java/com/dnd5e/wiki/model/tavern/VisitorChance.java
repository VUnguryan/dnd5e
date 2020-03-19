package com.dnd5e.wiki.model.tavern;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "taverna_visitor_chances")
@Getter
@Setter
public class VisitorChance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Enumerated(EnumType.STRING)
	private TavernaType tavernaType;
	@Enumerated(EnumType.STRING)
	private TavernaCategory tavernaCategory;
	private int chance;
	@ManyToOne
	private Visitor visitor;
}
