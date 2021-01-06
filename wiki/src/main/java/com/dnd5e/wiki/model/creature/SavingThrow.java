package com.dnd5e.wiki.model.creature;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dnd5e.wiki.model.AbilityType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter

@Entity
@Table(name = "bonus_saving_throws")
public class SavingThrow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Enumerated(EnumType.ORDINAL)
	private AbilityType ability;
	private byte bonus;
 
	public String getText() {
		return String.format("%s %+d ", ability.getShortName(), bonus);
	}
}