package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.creature.CreatureRace;

public interface CreatureRaceRepository extends JpaRepository<CreatureRace, Integer>{
	CreatureRace findByName(String name);
}
