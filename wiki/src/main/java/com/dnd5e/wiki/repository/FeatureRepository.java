package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.hero.race.Feature;

public interface FeatureRepository extends JpaRepository<Feature, Integer> {

}