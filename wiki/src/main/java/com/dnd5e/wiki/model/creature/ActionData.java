package com.dnd5e.wiki.model.creature;

import java.util.List;
import java.util.Set;

import javax.jdo.annotations.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dnd5e.wiki.model.AbilityType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "action_data")
public class ActionData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(defaultValue = "0")
	private byte attackBonus;
	private byte limitUse;
	
	@ElementCollection(targetClass = ActionDataType.class)
	@JoinTable(name = "action_data_types", joinColumns = @JoinColumn(name = "action_data_id"))
	@Column(name = "damage_type")
	@Enumerated(EnumType.STRING)
	private Set<ActionDataType> types;
	
	@OneToMany
	@JoinColumn(name = "action_id")
	private List<Damage> damages;
	
	private AbilityType savingThrowType;
	private Byte dc;
}
