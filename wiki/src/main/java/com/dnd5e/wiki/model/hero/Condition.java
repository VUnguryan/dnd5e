package com.dnd5e.wiki.model.hero;

import java.util.Arrays;

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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

@Entity
@Table(name = "conditions")
public class Condition {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String englishName;
	@Column(columnDefinition = "TEXT")
	private String description;
	@Enumerated(EnumType.STRING)
	private Type type;
	
	@ManyToOne
	@JoinColumn(name = "source")
	private Book book;
	private Short page;
	
	@AllArgsConstructor
	@Getter
	public enum Type {
		CONDITION("Состояние"),
		DISEASE("Болезнь"),
		OTHER("Прочие");
		private String name;
		
		public static Type parse(String type) {
			return Arrays.asList(values()).stream().filter(t -> t.getName().equals(type)).findFirst().orElse(CONDITION);
		}
	}
}