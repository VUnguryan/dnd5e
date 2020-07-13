package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.hero.PersonalizationFeature;

public interface PersonalizationFeatureRepository extends JpaRepository<PersonalizationFeature, Integer> {

}
