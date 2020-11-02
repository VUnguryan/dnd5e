package com.dnd5e.wiki.repository.datatable;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.hero.race.Race;

@Repository
public interface RaceDatatableRepository extends DataTablesRepository<Race, Integer> {
}
