package com.dnd5e.wiki.model.artifact;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
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
}