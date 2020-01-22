package com.dnd5e.wiki.model.hero;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dnd5e.wiki.model.Book;

import lombok.Data;

@Entity
@Table(name = "traits")
@Data
public class Trait {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String requirement;
	@Column(columnDefinition = "TEXT")
	private String description;
	@ManyToOne
	@JoinColumn(name = "source")
	private Book book;
}