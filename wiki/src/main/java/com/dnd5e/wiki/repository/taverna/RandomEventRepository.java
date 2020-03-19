package com.dnd5e.wiki.repository.taverna;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.tavern.RandomEvent;

public interface RandomEventRepository  extends JpaRepository<RandomEvent, Integer>  {
	List<RandomEvent> findByVisitorsLessThanEqual(int visitors);
}
