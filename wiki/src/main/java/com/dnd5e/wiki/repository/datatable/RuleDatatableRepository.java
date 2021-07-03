package com.dnd5e.wiki.repository.datatable;

import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.Rule;
import com.dnd5e.wiki.model.spell.GroupByCount;

@Repository
public interface RuleDatatableRepository extends DataTablesRepository<Rule, Integer>, JpaSpecificationExecutor<Rule> {

	@Query("SELECT c.type AS field, COUNT(c.type) AS total FROM Rule c GROUP BY c.type")
	List<GroupByCount<String>> countTotalTypes();
}
