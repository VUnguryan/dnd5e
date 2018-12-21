package com.dnd5e.wiki.model.tavern;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dnd5e.wiki.model.hero.classes.HeroClass;
import com.dnd5e.wiki.model.hero.race.Race;
import com.dnd5e.wiki.model.user.User;

import lombok.Data;

@Entity
@Table(name = "heroes")
@Data
public class Hero {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	
	private Integer exp;
	
	@ManyToOne
	@JoinColumn(name="race_id")
	private Race race;
	
	@ManyToOne
	@JoinColumn(name="class_id")
	private HeroClass heroClass;
	
	@ManyToOne
	@JoinColumn(name="usr_id")
	private User user;
}
