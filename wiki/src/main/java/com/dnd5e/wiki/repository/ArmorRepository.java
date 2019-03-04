package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.hero.Armor;

public interface ArmorRepository extends JpaRepository<Armor, Integer> {
}
