package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.hero.Race;

public interface RaceRepository extends JpaRepository<Race, Integer>{

}
