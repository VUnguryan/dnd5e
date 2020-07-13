package com.dnd5e.wiki.repository.datatable;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.hero.Condition;

@Repository
public interface ConditionDatatableRepository extends DataTablesRepository<Condition, Integer>, JpaSpecificationExecutor<Condition> {
}
