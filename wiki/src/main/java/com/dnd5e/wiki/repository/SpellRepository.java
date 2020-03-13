package com.dnd5e.wiki.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.spell.Spell;

@Repository
public abstract interface SpellRepository extends JpaRepository<Spell, Integer>, JpaSpecificationExecutor<Spell> {
	List<Spell> findByName(String paramString);

	@Query("SELECT s.distance FROM Spell s GROUP BY s.distance ORDER BY s.distance")
	List<String> findAllDistanceName();
	
	@Query("SELECT s FROM Spell s WHERE s.name LIKE %:name% OR englishName LIKE %:name% ")
	Page<Spell> findAllByName(@Param("name") String name, Pageable page);
}