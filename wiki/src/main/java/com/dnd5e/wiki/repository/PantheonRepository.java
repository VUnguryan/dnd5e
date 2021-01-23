package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.gods.Pantheon;

@Repository
public interface PantheonRepository extends JpaRepository<Pantheon, Integer> {

}