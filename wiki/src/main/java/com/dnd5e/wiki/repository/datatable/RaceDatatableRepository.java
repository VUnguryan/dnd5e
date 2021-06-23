package com.dnd5e.wiki.repository.datatable;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.AbilityType;
import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.creature.CreatureSize;
import com.dnd5e.wiki.model.hero.race.Race;
import com.dnd5e.wiki.model.spell.GroupByCount;

@Repository
public interface RaceDatatableRepository extends DataTablesRepository<Race, Integer> {

	@Query("SELECT r.book AS field, COUNT(r.book) AS total FROM Race r WHERE r.book.type IN :types GROUP BY r.book")
	List<GroupByCount<Book>> countTotalSpellByBook(@Param("types") Set<TypeBook> types);
	Collection<TypeBook> countTotalSpellByBook(Object object);

	@Query("SELECT b.ability AS field, COUNT(r.book) AS total FROM Race r JOIN r.bonuses b WHERE r.book.type IN :types GROUP BY b.ability")
	Collection<GroupByCount<AbilityType>> countTotalAbilities(@Param("types") Set<TypeBook> types);

	@Query("SELECT r.size AS field, COUNT(r.book) AS total FROM Race r WHERE r.book.type IN :types GROUP BY r.size")
	Collection<GroupByCount<CreatureSize>> countTotalSizes(@Param("types") Set<TypeBook> types);
}