package com.dnd5e.wiki.repository.datatable;

import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;

import com.dnd5e.wiki.model.creature.Alignment;
import com.dnd5e.wiki.model.gods.Domain;
import com.dnd5e.wiki.model.gods.God;
import com.dnd5e.wiki.model.gods.GodSex;
import com.dnd5e.wiki.model.gods.Pantheon;
import com.dnd5e.wiki.model.gods.Rank;
import com.dnd5e.wiki.model.spell.GroupByCount;

public interface GodDatatableRepository extends DataTablesRepository<God, Integer> {

	@Query("SELECT g.aligment AS field, COUNT(g.aligment) AS total FROM God g GROUP BY g.aligment")
	List<GroupByCount<Alignment>> countTotalGodAlignment();

	@Query("SELECT d AS field, COUNT(d) AS total FROM God g JOIN g.domains d GROUP BY d")
	List<GroupByCount<Domain>> countTotalGodDomain();

	@Query("SELECT g.pantheon AS field, COUNT(g.pantheon) AS total FROM God g GROUP BY g.pantheon")
	List<GroupByCount<Pantheon>> countTotalGodPantheon();

	@Query("SELECT g.sex AS field, COUNT(g.sex) AS total FROM God g GROUP BY g.sex")
	List<GroupByCount<GodSex>> countTotalGodSex();

	@Query("SELECT g.rank AS field, COUNT(g.rank) AS total FROM God g GROUP BY g.rank")
	List<GroupByCount<Rank>> countTotalGodRank();
}
