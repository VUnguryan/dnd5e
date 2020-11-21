package com.dnd5e.wiki.model.stock;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Subject {
	private String name;
	private String englishName;
	private int cost;
	private float weight;
}