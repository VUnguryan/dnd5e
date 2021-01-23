package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.gods.God;

@Repository
public interface GodRepository extends JpaRepository<God, Integer> {

}
