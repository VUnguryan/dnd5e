package com.dnd5e.wiki.model.creature;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ActionType {
	  ACTION("Действия"),
	  REACTION("Реакции"),
	  LEGENDARY("Легендарные действия");
	  private String name;
}