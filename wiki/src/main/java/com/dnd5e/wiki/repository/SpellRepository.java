package com.dnd5e.wiki.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.spell.Spell;

@Repository
public abstract interface SpellRepository extends JpaRepository<Spell, Integer>, JpaSpecificationExecutor<Spell> {
	boolean existsByName(String paramString);

	List<Spell> findByName(String paramString);

	Page<Spell> findByHeroClassId(Pageable page, Integer classId);
	
	@Query("SELECT s.distance FROM Spell s GROUP BY s.distance ORDER BY s.distance")
	Set<String> findAllDistanceName();
	
}