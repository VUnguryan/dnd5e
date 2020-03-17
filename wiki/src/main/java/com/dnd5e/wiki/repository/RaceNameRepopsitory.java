package com.dnd5e.wiki.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.hero.race.Race;
import com.dnd5e.wiki.model.hero.race.RaceName;

public interface RaceNameRepopsitory extends JpaRepository<RaceName, Integer> {
	Set<RaceName> findByRace(Race race);
}
