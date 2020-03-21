package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.spell.WildMagic;

public interface WildMagicRepository extends JpaRepository<WildMagic, Integer> {

}
