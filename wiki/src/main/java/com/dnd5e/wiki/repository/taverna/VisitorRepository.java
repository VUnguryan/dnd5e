package com.dnd5e.wiki.repository.taverna;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dnd5e.wiki.model.tavern.TavernaCategory;
import com.dnd5e.wiki.model.tavern.TavernaType;
import com.dnd5e.wiki.model.tavern.Visitor;

public interface VisitorRepository extends JpaRepository<Visitor, Integer> {
	public final static String QUERY = 
			"SELECT v                                                     " + 
            "FROM Visitor v LEFT JOIN v.chance c                          " +
            "WHERE c.tavernaType = :type AND c.tavernaCategory = :category";

	@Query(QUERY)
	List<Visitor> findByTavernaTypeAndTavernaCategory(@Param("type") TavernaType type, @Param("category") TavernaCategory category);
}
 