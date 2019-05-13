package com.dnd5e.wiki.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.tavern.Hero;

public interface HeroRepository extends JpaRepository<Hero, Integer> {

	Page<Hero> findByUserId(Long id, Pageable pagable);

}
