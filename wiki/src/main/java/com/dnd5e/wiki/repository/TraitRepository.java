package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.hero.Trait;

public interface TraitRepository extends JpaRepository<Trait, Integer> {

}
