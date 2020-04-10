package com.dnd5e.wiki.repository.taverna;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.tavern.DrinkEffect;

public interface DrinkEffectsRepository extends JpaRepository<DrinkEffect, Integer>{

}
