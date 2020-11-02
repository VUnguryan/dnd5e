package com.dnd5e.wiki.repository.datatable;

import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dnd5e.wiki.model.AbilityType;
import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.creature.SkillType;
import com.dnd5e.wiki.model.hero.Trait;
import com.dnd5e.wiki.model.spell.GroupByCount;

@Repository
public interface TraitDatatableRepository extends DataTablesRepository<Trait, Integer> {

	List<Trait> findAll();
	
	@Query("SELECT a AS field, COUNT(a) AS total FROM Trait s JOIN s.abilities AS a GROUP BY a")
	List<GroupByCount<AbilityType>> countTotalTraitAbilyty();
	
	@Query("SELECT sk AS field, COUNT(sk) AS total FROM Trait s JOIN s.skills AS sk GROUP BY sk")
	List<GroupByCount<SkillType>> countTotalTraitSkill();
	
	@Query("SELECT b AS field, COUNT(b) AS total FROM Trait s JOIN s.book AS b GROUP BY b")
	List<GroupByCount<Book>> countTotalTraitBook();
}
