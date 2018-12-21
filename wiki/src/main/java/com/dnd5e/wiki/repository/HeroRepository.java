package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.tavern.Hero;

public interface HeroRepository extends JpaRepository<Hero, Integer> {

}
