package com.dnd5e.wiki.model.spell;

public interface GroupByCount<T> {
	T getField();
	Long getTotal();
}
