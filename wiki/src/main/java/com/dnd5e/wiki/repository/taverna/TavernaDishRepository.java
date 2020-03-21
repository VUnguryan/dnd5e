package com.dnd5e.wiki.repository.taverna;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.creature.HabitatType;
import com.dnd5e.wiki.model.tavern.TavernaCategory;
import com.dnd5e.wiki.model.tavern.TavernaDish;

public interface TavernaDishRepository extends JpaRepository<TavernaDish, Integer>{
	List<TavernaDish> findByCategory(TavernaCategory category);
	List<TavernaDish> findByHabitat(HabitatType habitat);
}
