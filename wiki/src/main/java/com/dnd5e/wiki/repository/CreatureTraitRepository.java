package com.dnd5e.wiki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.creature.CreatureFeat;

@Repository
public interface CreatureTraitRepository extends JpaRepository<CreatureFeat, Integer> {
	public abstract List<CreatureFeat> findByNameContaining(String paramString);
}