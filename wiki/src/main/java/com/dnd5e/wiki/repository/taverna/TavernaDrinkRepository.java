package com.dnd5e.wiki.repository.taverna;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.creature.HabitatType;
import com.dnd5e.wiki.model.tavern.TavernaCategory;
import com.dnd5e.wiki.model.tavern.TavernaDrink;

public interface TavernaDrinkRepository extends JpaRepository<TavernaDrink, Integer>{
	List<TavernaDrink> findByCategory(TavernaCategory category);
	List<TavernaDrink> findByHabitat(HabitatType habitat);

}
