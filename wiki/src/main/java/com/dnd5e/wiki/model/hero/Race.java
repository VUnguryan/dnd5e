package com.dnd5e.wiki.model.hero;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dnd5e.wiki.model.feat.Feat;

import lombok.Data;

@Entity
@Table(name = "races")
@Data
public class Race {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private int speed;
	@Column(columnDefinition = "TEXT")
	private String description;
	@OneToMany
	private List<Bonus> bonuses;
	@OneToMany
	private List<Feat> feats;
}
