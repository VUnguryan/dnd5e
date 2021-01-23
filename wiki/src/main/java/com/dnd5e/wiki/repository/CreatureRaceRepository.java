package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.creature.CreatureRace;

@Repository
public interface CreatureRaceRepository extends JpaRepository<CreatureRace, Integer>{
	CreatureRace findByName(String name);
}
