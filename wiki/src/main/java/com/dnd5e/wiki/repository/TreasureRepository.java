package com.dnd5e.wiki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.treasure.Treasure;
import com.dnd5e.wiki.model.treasure.TreasureType;

@Repository
public interface TreasureRepository extends JpaRepository<Treasure, Integer>{
	List<Treasure> findAllByCostAndType(int cost, TreasureType type);
}