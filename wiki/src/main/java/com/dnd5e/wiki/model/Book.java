package com.dnd5e.wiki.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "books")
public class Book {
	@Id
	@Column(unique = true, nullable = false)
	@Size(max = 32)
	private String source;
	private String name;
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@Enumerated(EnumType.STRING) 
	private TypeBook type;
}