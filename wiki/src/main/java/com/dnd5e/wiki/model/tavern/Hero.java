package com.dnd5e.wiki.model.tavern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dnd5e.wiki.model.hero.classes.Archetype;
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

	@Column(nullable = false, columnDefinition = "int default 0")
	private int exp;

	@ManyToOne
	@JoinColumn(name = "race_id")
	private Race race;

	@ManyToOne
	@JoinColumn(name = "class_id")
	private HeroClass heroClass;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "archetype_id")
	private Archetype archetype;
	
	@ManyToOne
	@JoinColumn(name = "usr_id")
	private User user;

	// Хаактеристики
	private byte strength = 10;
	private byte dexterity = 10;
	private byte constitution = 10;
	private byte intellect = 10;
	private byte wizdom = 10;
	private byte charisma = 10;
	
	private short hp;
	private short maxHp;
	
	private byte glory;
	private int idleDays;
	private int heroism;
	
	// Монеты
	private int copper;
	private int silver;
	private int gold;
	private int electrium;
	private int platinum;
	
	public int getAc() {
		if ("ВАРВАР".equals(heroClass.getName())) {
			return 10 + getDexBonus() + getConBonus();
		}
		if ("МОНАХ".equals(heroClass.getName())) {
			return 10 + getDexBonus() + getWizBonus();
		}

		return 10 + getDexBonus();
	}
	public int getLevel() {
		return HeroUtil.getLevel(exp);
	}

	public void setLevel(int level) {
		this.exp = HeroUtil.getStartLevelExp(level);
	}

	public int getStrBonus() {
		return HeroUtil.getModifier(strength);
	}

	public int getDexBonus() {
		return HeroUtil.getModifier(dexterity);
	}

	public int getConBonus() {
		return HeroUtil.getModifier(constitution);
	}

	public int getIntBonus() {
		return HeroUtil.getModifier(intellect);
	}

	public int getWizBonus() {
		return HeroUtil.getModifier(wizdom);
	}

	public int getChrBonus() {
		return HeroUtil.getModifier(charisma);
	}

	public int getNextLevelExp() {
		return HeroUtil.getStartLevelExp(HeroUtil.getLevel(exp)+1) - exp;
	}
}