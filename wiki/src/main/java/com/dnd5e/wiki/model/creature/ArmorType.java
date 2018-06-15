package com.dnd5e.wiki.model.creature;

public enum ArmorType
{
  LEATHER("кожанный доспех"),
  NATURAL("природный доспех"),  
  PLATE("латы"),
  WRECKAGE("");
  
  private String cyrillicName;
  
  private ArmorType(String cyrillicName)
  {
    this.cyrillicName = cyrillicName;
  }
  
  public String getCyrillicName()
  {
    return this.cyrillicName;
  }
}