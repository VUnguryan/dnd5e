package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.creature.Language;

@Repository
public interface LanguagesRepository extends JpaRepository<Language, Integer>{
	Language findByName(String name);
}
