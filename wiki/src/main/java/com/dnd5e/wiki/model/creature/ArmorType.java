package com.dnd5e.wiki.model.creature;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArmorType {
	NATURAL("природный доспех"), // 0
	LEATHER("кожанный доспех"), // 1
	HIDE("шкурный доспех"), // 2
	CHAINMAIL("кольчужная рубаха"), //3 
	RING_MAIL("колечный доспех"),
	SCRAPPY("лоскутный доспех"), // 4
	SCALED("чешуйчатый доспех"), //5 
	RIVETED_LEATHER("проклёпаная кожа"), //7
	CHAIN_MAIL("кольчуга"),
	HALF_PLATE("полулаты"),
	PLATE("латный доспех"),
	
	PLATE_HALF("пластинчатый доспех"),
	CUIRASS("кираса"),
	MAGE_ARMOR("с доспехами мага"),
	
	SHIELD("щит"); //9

	private String cyrillicName;
}