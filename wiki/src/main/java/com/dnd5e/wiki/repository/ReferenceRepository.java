package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.Reference;

public interface ReferenceRepository extends JpaRepository<Reference, Integer>{

}
