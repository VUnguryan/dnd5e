package com.dnd5e.wiki.model.creature;

public enum CreatureType {
	  ABERRATION("Аберация"),  
	  ANIMAL("Зверь"),
	  CELESTIAL("Небожитель"),
	  CONSTRUCT("Конструкт"),
	  DRAGON("Дракон"),
	  ELEMENTAL("Элементаль"),
	  FEY("Фей"),
	  FIEND("Исчадие"),
	  GIANT("Великан"),
	  HUMANOID("Гуманойд"),
	  MAGICAl_BEAST("Магический зверь"),
	  OOZE("Тина"),
	  OUTSIDER("Потустаронний"),
	  PLANT("Растение"),
	  DEVIL("Демон"),
	  UNDEAD("Нежить"),
	  VERMIN("Паразит");
	  
	  private String cyrilicName;
	  
	  private CreatureType(String cyrilicName)
	  {
	    this.cyrilicName = cyrilicName;
	  }
	  
	  public String getCyrilicName()
	  {
	    return this.cyrilicName;
	  }
}
