package com.dnd5e.wiki.model.gods;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "gods")
@Getter
@Setter
public class God {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String commitment;

	@Column(columnDefinition = "TEXT")
	private String description;
	private String symbol;

	@ElementCollection(targetClass=Domain.class)
    @CollectionTable(name="god_domains")
	@Enumerated(EnumType.STRING)
	private List<Domain> domains;
}
