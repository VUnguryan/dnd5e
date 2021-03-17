package com.dnd5e.wiki.repository.datatable;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.creature.Creature;
import com.dnd5e.wiki.model.creature.CreatureSize;
import com.dnd5e.wiki.model.creature.CreatureType;
import com.dnd5e.wiki.model.spell.GroupByCount;

@Repository
public abstract interface CreatureDatatableRepository extends DataTablesRepository<Creature, Integer> {

	@Query("SELECT c.challengeRating AS field, COUNT(c.challengeRating) AS total FROM Creature c GROUP BY c.challengeRating")
	List<GroupByCount<String>> countTotalCreatureByCr();

	@Query("SELECT c.type AS field, COUNT(c.type) AS total FROM Creature c GROUP BY c.type")
	List<GroupByCount<CreatureType>>  countTotalCreatureByType();

	@Query("SELECT c.size AS field, COUNT(c.size) AS total FROM Creature c GROUP BY c.size")
	List<GroupByCount<CreatureSize>> countTotalCreatureBySize();

	@Query("SELECT c.book AS field, COUNT(c.book) AS total FROM Creature c WHERE c.book.type IN :types GROUP BY c.book")
	List<GroupByCount<Book>> countTotalCreatureByBook(@Param("types") Set<TypeBook> types);
}