package com.dnd5e.wiki.model.creature;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dnd5e.wiki.model.Book;

import lombok.Getter;
import lombok.Setter;

/**
 * Описание существа
 */
@Entity
@Table(name = "creatures")
@Getter
@Setter
public class Creature {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String englishName;
	
	@Enumerated(EnumType.ORDINAL)
	private CreatureSize size;

	@Enumerated(EnumType.ORDINAL)
	private CreatureType type;

	private Integer raceId;
	private String raceName;

	@Enumerated(EnumType.ORDINAL)
	private Alignment alignment;

	private byte AC;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<ArmorType> armorTypes;

	private short averageHp;
	private short countDiceHp;

	@Enumerated(EnumType.ORDINAL)
	private Dice diceHp;

	@Column(nullable = true)
	private Short bonusHP;

	private byte speed = 30;
	@Column(nullable = true)
	private Short flySpeed;
	@Column(nullable = true)
	private Short swimmingSpped;
	@Column(nullable = true)
	private Short climbingSpeed;
	@Column(nullable = true)
	private Short diggingSpeed;

	// Абилки
	private byte strength = 10;
	private byte dexterity = 10;
	private byte constitution = 10;
	private byte intellect = 10;
	private byte wizdom = 10;
	private byte charisma = 10;

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

	private Integer darkvision;
	private Integer	trysight;
	private Integer	vibration;
	private Integer	blindsight;
	private Integer	blindsightRadius;
	
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

	@ManyToMany
	private List<Language> languages;

	@OneToMany(cascade = CascadeType.ALL)
	private List<CreatureTrait> feats;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Action> actions;

	@Column(columnDefinition = "TEXT")
	private String description;
	
	@Column(columnDefinition = "TEXT")
	private String legendary;
	
	@ManyToMany
	private List<CreatureRace> races;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<HabitatType> habitates;
	
	@ManyToOne
	@JoinColumn(name = "source")
	private Book book;
	
	public String getSizeName() {
		return size.getSizeName(type);
	}
	
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
		return String.format("%d (%+d)", ability, (ability - 10) < 0 ? (ability - 11) / 2 : (ability - 10) / 2);
	}
	
	public String getArmorTypeString() {
		return armorTypes.stream().map(ArmorType::getCyrillicName).collect(Collectors.joining(", "));
	}
	
	public String getHp() {
		if (bonusHP == null && diceHp == null) {
			return String.format("%d", averageHp);
		}
		if (bonusHP == null) {
			return String.format("%d (%d%s)" , averageHp, countDiceHp, diceHp.name());
		}
		return String.format("%d (%d%s + %d)" , averageHp, countDiceHp, diceHp.name(), bonusHP);
	}

	public String getSense() {
		List<String> sense = new ArrayList<String>(5);
		if (blindsight != null) {
			String blind = String.format("слепое зрение %d фт.", blindsight);
			if (blindsightRadius != null) {
				blind += " (слеп за пределами этого радиуса)";
			}
			sense.add(blind);
		}
		if (darkvision!=null) {
			sense.add(String.format("тёмное зрение %d фт.", darkvision));
		}
		if (trysight != null) {
			sense.add(String.format("истинное зрение %d фт.", trysight));
		}
		if (vibration != null) {
			sense.add(String.format("чувство вибрации %d фт.", vibration));
		}
		return sense.stream().collect(Collectors.joining(", "));  
	}

	public String getAllSense() {
		List<String> sense = new ArrayList<String>(5);
		if (blindsight != null) {
			String blind = String.format("слепое зрение %d фт.", blindsight);
			if (blindsightRadius != null) {
				blind += " (слеп за пределами этого радиуса)";
			}
			sense.add(blind);
		}
		if (darkvision!=null) {
			sense.add(String.format("тёмное зрение %d фт.", darkvision));
		}
		if (trysight != null) {
			sense.add(String.format("истинное зрение %d фт.", trysight));
		}
		if (vibration != null) {
			sense.add(String.format("чувство вибрации %d фт.", vibration));
		}
		sense.add(String.format("пассивная Внимательность %d", passivePerception));
		
		return sense.stream().collect(Collectors.joining(", "));  
	}
	
	public String getAllSpeedEnglish() {
		return String.format("%d ft.", speed)
				+ (flySpeed == null ? "": String.format(", fly %d ft.", flySpeed))
				+ (swimmingSpped == null ? "": String.format(", swim %d ft.", swimmingSpped))
				+ (diggingSpeed == null ? "": String.format(", burrow %d ft.", diggingSpeed))
				+ (climbingSpeed == null ? "": String.format(", climb %d ft.", climbingSpeed));
	}
	
	public String getAllSpeed() {
		return String.format("%d фт.", speed)
				+ (flySpeed == null ? "": String.format(", летая %d фт.", flySpeed))
				+ (swimmingSpped == null ? "": String.format(", плавая %d фт.", swimmingSpped))
				+ (diggingSpeed == null ? "": String.format(", копая %d фт.", diggingSpeed))
				+ (climbingSpeed == null ? "": String.format(", лазая %d фт.", climbingSpeed));
	}
}