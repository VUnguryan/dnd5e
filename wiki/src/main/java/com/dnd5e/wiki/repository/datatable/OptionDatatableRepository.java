package com.dnd5e.wiki.repository.datatable;

import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.hero.Option;
import com.dnd5e.wiki.model.hero.Option.OptionType;
import com.dnd5e.wiki.model.spell.GroupByCount;

@Repository
public interface OptionDatatableRepository extends DataTablesRepository<Option, Integer> {
	@Query("SELECT b AS field, COUNT(b) AS total FROM Option o JOIN o.book AS b GROUP BY b")
	List<GroupByCount<Book>> countTotalOptionBook();
	
	@Query("SELECT t AS field, COUNT(t) AS total FROM Option o LEFT JOIN o.optionTypes t GROUP BY t")
	List<GroupByCount<OptionType>> countTotalOptionType();

	@Query("SELECT o.prerequisite AS field, COUNT(*) AS total FROM Option o GROUP BY o.prerequisite")
	List<GroupByCount<String>> countTotalPrerequisite();
	
	@Query("SELECT o.prerequisite AS field, COUNT(*) AS total FROM Option o JOIN o.optionTypes t WHERE t IN :types GROUP BY o.prerequisite")
	List<GroupByCount<String>> countTotalPrerequisite(@Param("types") List<OptionType> types);
}