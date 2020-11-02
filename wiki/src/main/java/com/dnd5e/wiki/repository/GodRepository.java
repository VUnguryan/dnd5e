package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.gods.God;

public interface GodRepository extends JpaRepository<God, Integer> {

}
