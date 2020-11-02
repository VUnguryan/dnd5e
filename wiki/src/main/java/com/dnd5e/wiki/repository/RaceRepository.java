package com.dnd5e.wiki.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.hero.race.Race;

@Repository
public interface RaceRepository extends JpaRepository<Race, Integer>{
	List<Race> findByParentIsNull(Sort sort);
}
