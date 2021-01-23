package com.dnd5e.wiki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.places.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer> {
	List<Place> findByParentIsNull();
}
