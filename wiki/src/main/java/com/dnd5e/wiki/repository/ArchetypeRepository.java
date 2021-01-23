package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.hero.classes.Archetype;

@Repository
public interface ArchetypeRepository extends JpaRepository<Archetype, Integer> {

}
