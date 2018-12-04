package com.dnd5e.wiki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.paces.Place;

public interface PlaceRepository extends JpaRepository<Place, Integer> {

	List<Place> findByParentIsNull();
}
