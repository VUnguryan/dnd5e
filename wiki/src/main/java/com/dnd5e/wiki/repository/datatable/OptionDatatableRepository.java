package com.dnd5e.wiki.repository.datatable;

import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.hero.Option;
import com.dnd5e.wiki.model.hero.Option.OptionType;
import com.dnd5e.wiki.model.spell.GroupByCount;

@Repository
public interface OptionDatatableRepository extends DataTablesRepository<Option, Integer> {
	@Query("SELECT b AS field, COUNT(b) AS total FROM Option o JOIN o.book AS b GROUP BY b")
	List<GroupByCount<Book>> countTotalOptionBook();
	
	@Query("SELECT o.optionType AS field, COUNT(o.optionType) AS total FROM Option o GROUP BY o.optionType")
	List<GroupByCount<OptionType>> countTotalOptionType();
}