package com.dnd5e.wiki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dnd5e.wiki.model.creature.HabitatType;
import com.dnd5e.wiki.model.encounter.RandomEncounter;

public interface RandomEncounterRepository extends JpaRepository<RandomEncounter, Integer> {
	@Query("SELECT e FROM RandomEncounter e WHERE e.start >= :index AND e.end <= :index AND e.level = :level AND e.type = :type")
	RandomEncounter findOne(@Param("index") int index, @Param("level") int level,
			@Param("type") HabitatType type);

	List<RandomEncounter> findAllByLevelAndType(Integer level, HabitatType type);
}