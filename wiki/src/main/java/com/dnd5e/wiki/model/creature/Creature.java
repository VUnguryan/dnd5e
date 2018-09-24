package com.dnd5e.wiki.model.creature;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dnd5e.wiki.model.feat.Feat;

import lombok.Data;

@Entity
@Table(name = "creatures")
@Data
public class Creature {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String wnglishName;
	
	@Enumerated(EnumType.ORDINAL)
	private CreatureSize size;

	@Enumerated(EnumType.ORDINAL)
	private CreatureType type;

	private Integer raceId;
	private String raceName;

	@Enumerated(EnumType.ORDINAL)
	private Alignment alignment;

	private byte AC;

	@Enumerated(EnumType.ORDINAL)
	private ArmorType armorType;

	private short averageHp;
	private short countDiceHp;

	@Enumerated(EnumType.ORDINAL)
	private Dice diceHp;

	@Column(nullable = true)
	private Short bonusHP;

	private short speed;
	@Column(nullable = true)
	private Short flySpeed;
	@Column(nullable = true)
	private Short swimmingSpped;
	@Column(nullable = true)
	private Short climbingSpeed;
	@Column(nullable = true)
	private Short diggingSpeed;

	// Абилки
	private byte strength;
	private byte dexterity;
	private byte constitution;
	private byte intellect;
	private byte wizdom;
	private byte charisma;

	@ElementCollection
	@Enumerated(EnumType.ORDINAL)
	private List<State> immunityStates;

	@ElementCollection
	@Enumerated(EnumType.ORDINAL)
	private List<DamageType> immunityDamages;

	@ElementCollection
	@Enumerated(EnumType.ORDINAL)
	private List<DamageType> resistanceDamages;

	@ElementCollection
	@Enumerated(EnumType.ORDINAL)
	private List<DamageType> vulnerabilityDamages;

	private String senses;
	private byte passivePerception;

	// опыт
	private int exp;
	// уровень опасности
	private String challengeRating;

	// спаброски
	@OneToMany(cascade = CascadeType.ALL)
	private List<SavingThrow> savingThrows;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Skill> skills;

	private String vision;

	@ManyToMany
	private List<Language> languages;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Feat> feats;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Action> actions;

	@Column(columnDefinition = "TEXT")
	private String description;
	
	@Column(columnDefinition = "TEXT")
	private String legendary;
	
	public String strengthText() {
		return getFormatAbility(strength);
	}

	public String dexterityText() {
		return getFormatAbility(dexterity);
	}

	public String constitutionText() {
		return getFormatAbility(constitution);
	}

	public String intellectText() {
		return getFormatAbility(intellect);
	}

	public String wizdomText() {
		return getFormatAbility(wizdom);
	}

	public String charismaText() {
		return getFormatAbility(charisma);
	}

	private String getFormatAbility(byte ability) {
		return String.format("%d (%+d)", ability, ability - 10 < 10 ? (ability - 11) / 2 : (ability - 10) / 2);
	}
}