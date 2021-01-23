package com.dnd5e.wiki.controller.rest.model.xml;

import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import org.thymeleaf.util.StringUtils;

import com.dnd5e.wiki.model.stock.Armor;
import com.dnd5e.wiki.model.stock.ArmorType;
import com.dnd5e.wiki.model.stock.Currency;
import com.dnd5e.wiki.model.stock.Equipment;
import com.dnd5e.wiki.model.stock.EquipmentType;
import com.dnd5e.wiki.model.stock.Weapon;
import com.dnd5e.wiki.model.stock.WeaponProperty;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemVO {
	@XmlElement
	private String name;
	@XmlElement(required = false)
	private String type;
	@XmlElement(required = false)
	private String text;
	@XmlElement(required = false)
	private float weight;
	@XmlElement()
	private float value;

	@XmlElement(required = false)
	private Integer ac;

	@XmlElement(required = false)
	private Integer strength;

	@XmlElement(required = false)
	private String stealth;

	@XmlElement(required = false)
	private String dmg1;
	@XmlElement(required = false)
	private String dmg2;
	@XmlElement(required = false)
	private String dmgType;
	
	@XmlElement(required = false)
	private String range;
	
	@XmlElement(required = false)
	private String property;

	public ItemVO(Equipment equipment) {
		this.name = StringUtils.capitalize(equipment.getName().toLowerCase()).trim();
		this.text = Compendium.removeHtml(equipment.getDescription()).trim();
		Set<EquipmentType> types = equipment.getTypes();
		if (types.contains(EquipmentType.ADVENTURING_GEAR)) {
			this.type = "G";	
		}
		if (types.contains(EquipmentType.MELE_WAPON)) {
			this.type = "M";	
		}
		if (types.contains(EquipmentType.RANGE_WAPON)) {
			this.type = "R";	
		}
		if (types.contains(EquipmentType.HEAVY_ARMOR)) {
			this.type = "HA";	
		}
		if (equipment.getCost()!=null) {
			this.value = Math.round(100 * Currency.GM.convert(equipment.getCurrency(), equipment.getCost())) / 100f;
		}
		if (equipment.getWeight()!=null) {
			this.weight = equipment.getWeight();
		}
	}

	public ItemVO(Weapon weapon) {
		this.name = StringUtils.capitalize(weapon.getName().toLowerCase());
		this.text = Compendium.removeHtml(weapon.getDescription());
		switch (weapon.getType()) {
		case EXOTIC_MELE:
		case SIMPLE_MELE:
		case WAR_MELE:
			this.type = "M";
			break;
		case EXOTIC_RANGED:
		case SIMPLE_RANGED:
		case WAR_RANGED:
			this.type = "R";
			break;
		}
		property = weapon.getProperties()
				.stream()
				.map(this::getPropertyLiteral)
				.filter(s -> !s.isEmpty())
				.collect(Collectors.joining(","));
		
		dmg1 = weapon.getNumberDice() == null ? "" : String.valueOf(weapon.getNumberDice());
		dmg1 += weapon.getDamageDice() == null ? "" : weapon.getDamageDice().name();
		if (weapon.getMinDistance() != null && weapon.getMaxDistance() != null)
		{
			range = weapon.getMinDistance() + "/"+ weapon.getMaxDistance();
		}
		if (weapon.getTwoHandDamageDice() != null)
		{
			dmg2 = weapon.getNumberDice() == null ? "" : String.valueOf(weapon.getNumberDice()) + weapon.getTwoHandDamageDice();
		}
		if (weapon.getDamageType()!= null)
		{
			dmgType = String.valueOf(weapon.getDamageType().name().charAt(0));
		}
		value = Math.round(100 * Currency.GM.convert(weapon.getCurrency(), weapon.getCost())) / 100f;
		weight = weapon.getWeight();
	}

	public ItemVO(Armor armor) {
		name = StringUtils.capitalize(armor.getName().toLowerCase());
		text = Compendium.removeHtml(armor.getDescription());
		if (armor.getType() == ArmorType.HEAVY) {
			type = "HA";
		} else if (armor.getType() == ArmorType.MEDIUM) {
			type = "MA";
		} else if (armor.getType() == ArmorType.LIGHT) {
			type = "LA";
		} else if (armor.getType() == ArmorType.SHIELD) {
			type = "S";
		}
		ac = armor.getAC();
		value = Math.round(armor.getCost());
		if (armor.getForceRequirements() != null) {
			strength = armor.getForceRequirements();
		}
		stealth = armor.isStelsHindrance() ? "YES" : "NO";
		weight = armor.getWeight();
	}

	private String getPropertyLiteral(WeaponProperty property) {
		switch (property.getName()) {
		case "Боеприпас":
			return "A";
		case "Фехтовальное":
			return "F";
		case "Тяжёлое":
			return "H";
		case "Лёгкое":
			return "L";
		case "Перезарядка":
			return "LD";
		case "Досягаемость":
			return "R";
		case "Особое":
			return "S";
		case "Метательное":
			return "T";
		case "Двуручное":
			return "2H";
		case "Универсальное":
			return "V";
		default:
			return "";
		}
	}
}