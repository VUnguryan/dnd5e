package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.hero.HeroClassFeat;

@Repository
public interface HeroClassFeatRepository extends JpaRepository<HeroClassFeat, Integer> {

}
