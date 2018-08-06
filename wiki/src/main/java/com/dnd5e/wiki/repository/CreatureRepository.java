package com.dnd5e.wiki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.creature.Creature;

@Repository
public abstract interface CreatureRepository extends JpaRepository<Creature, Integer> {

	List<Creature> findByNameContaining(String search);

	List<Creature> findAllByRaceIdOrderByExpAsc(Integer id);
}