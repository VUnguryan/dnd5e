package com.dnd5e.wiki.model.treasure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dnd5e.wiki.model.Book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "treasures")
@Getter
@Setter
@NoArgsConstructor
public class Treasure {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "TEXT")
	private String name;
	private int cost;
	
	@Enumerated(EnumType.STRING)
	private TreasureType type;
	@ManyToOne
	@JoinColumn(name = "source")
	private Book book;
	private Short page;
}