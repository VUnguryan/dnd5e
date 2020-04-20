package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.gods.Pantheon;

public interface PantheonRepository extends JpaRepository<Pantheon, Integer> {

}
