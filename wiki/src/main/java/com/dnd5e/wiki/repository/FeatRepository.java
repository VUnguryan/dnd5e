package com.dnd5e.wiki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.feat.Feat;

public interface FeatRepository extends JpaRepository<Feat, Integer> {
	public abstract List<Feat> findByNameContaining(String paramString);

}
