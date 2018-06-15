package com.dnd5e.wiki.model.artifact;

public enum Rarity
{
  COMMON("обычный"),
  UNCOMON("необычный"),
  RARE("редкий"),
  VERY_RARE("очень редкий"),
  LEGENDARY("легендарный");
  
  private String cyrilicName;
  
  private Rarity(String cyrilicName)
  {
    this.cyrilicName = cyrilicName;
  }
  
  public String getCyrilicName()
  {
    return this.cyrilicName;
  }
}