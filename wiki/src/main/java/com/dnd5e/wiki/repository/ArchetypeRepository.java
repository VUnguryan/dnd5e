package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.hero.classes.Archetype;

public interface ArchetypeRepository extends JpaRepository<Archetype, Integer> {

}
