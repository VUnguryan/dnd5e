package com.dnd5e.wiki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.creature.CreatureTrait;

public interface CreatureTraitRepository extends JpaRepository<CreatureTrait, Integer> {
	public abstract List<CreatureTrait> findByNameContaining(String paramString);

}
