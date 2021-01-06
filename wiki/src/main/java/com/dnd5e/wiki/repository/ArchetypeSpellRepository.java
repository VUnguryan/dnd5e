package com.dnd5e.wiki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.hero.classes.Archetype;
import com.dnd5e.wiki.model.hero.classes.ArchetypeSpell;

@Repository
public interface ArchetypeSpellRepository extends JpaRepository<ArchetypeSpell, Integer>{
	@Query(value = "SELECT a FROM Archetype a JOIN a.spells s WHERE s.spell.id = :spellId")
	List<Archetype> findAllBySpellId(Integer spellId);
}