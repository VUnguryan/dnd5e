package com.dnd5e.wiki.repository.taverna;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.tavern.Atmosphere;

public interface AtmosphereRepoditory  extends JpaRepository<Atmosphere, Integer> {
	List<Atmosphere> findByVisitorsLessThanEqual(int visitors);
}
