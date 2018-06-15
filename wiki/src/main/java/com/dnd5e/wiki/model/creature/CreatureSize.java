package com.dnd5e.wiki.model.creature;

public enum CreatureSize
{
  TINY("Крошечный"),
  SMALL("Маленький"),
  MEDIUM("Средний"),
  LARGE("Большой"),
  HUGE("Огромный"),
  GARGANTUM("Гигантский");
  
  private String cyrilicName;
  
  private CreatureSize(String cyrilicName)
  {
    this.cyrilicName = cyrilicName;
  }
  
  public String getCyrilicName()
  {
    return this.cyrilicName;
  }
}