package com.dnd5e.wiki.model.artifact;

public enum ArtifactType
{
  WEAPON("оружие"),
  ARMOR("доспех"),
  WAND("волшебная палочка"),
  ROD("жезл"),  
  STAFF("посох"),
  POTION("зелье"),
  RING("кольцо"),  
  SCROLL("свиток"),
  SUBJECT("чудесный предмет");
  
  private String cyrilicName;
  
  private ArtifactType(String cyrilicName)
  {
    this.cyrilicName = cyrilicName;
  }
  
  public String getCyrilicName()
  {
    return this.cyrilicName;
  }
}