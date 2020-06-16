package com.dnd5e.wiki.model.treasure;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MagicThingType
{
  MELE_WEAPON("рукопашное оружие"), //0
  ARMOR("доспех"), // 1
  WAND("волшебная палочка"), // 2
  ROD("жезл"),  // 3
  STAFF("посох"), //4 
  POTION("зелье"), //5
  RING("кольцо"),  //6
  SCROLL("свиток"), // 7
  SUBJECT("чудесный предмет"), // 8
  RANGED_WEAPON("дальнобойное оружие"), //9
  SHIELD("щит"),  // 10
  AMMUNITION("аммуниция"); //11

  private String cyrilicName;
  public static MagicThingType parse(String value) {
	  return Arrays.stream(values()).filter(t -> t.getCyrilicName().equals(value)).findFirst().orElseThrow(IllegalArgumentException::new);
  }
}