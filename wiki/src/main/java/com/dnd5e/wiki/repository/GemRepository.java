package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.treasure.Gem;

public interface GemRepository extends JpaRepository<Gem, Integer>{

}
