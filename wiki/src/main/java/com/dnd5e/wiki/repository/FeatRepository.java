package com.dnd5e.wiki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.feat.Trait;

public interface FeatRepository extends JpaRepository<Trait, Integer> {
	public abstract List<Trait> findByNameContaining(String paramString);

}
