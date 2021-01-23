package com.dnd5e.wiki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.hero.madness.Madness;
import com.dnd5e.wiki.model.hero.madness.MadnessType;

@Repository
public interface MadnessRepository extends JpaRepository<Madness, Integer>{
	List<Madness> findByMadnessType(MadnessType madnessType); 
}
