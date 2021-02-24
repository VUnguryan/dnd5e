package com.dnd5e.wiki.repository.datatable;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;

import com.dnd5e.wiki.model.Book;
import com.dnd5e.wiki.model.spell.GroupByCount;
import com.dnd5e.wiki.model.treasure.MagicThing;
import com.dnd5e.wiki.model.treasure.MagicThingType;
import com.dnd5e.wiki.model.treasure.Rarity;

public interface ArtifactRepository extends DataTablesRepository<MagicThing, Integer> {
	List<MagicThing> findAll();
	Page<MagicThing> findByNameContaining(Pageable pagable, String paramString);
	
	@Query("SELECT m.rarity AS field, COUNT(m.rarity) AS total FROM MagicThing m GROUP BY m.rarity")
	List<GroupByCount<Rarity>> countTotalMagicThingsByRarity();
	
	@Query("SELECT m.type AS field, COUNT(m.type) AS total FROM MagicThing m GROUP BY m.type")
	List<GroupByCount<MagicThingType>> countTotalMagicThingsByType();
	
	@Query("SELECT m.customization AS field, COUNT(m.customization) AS total FROM MagicThing m GROUP BY m.customization")
	List<GroupByCount<Boolean>> countTotalMagicThingsByCustomization();
	
	@Query("SELECT m.book AS field, COUNT(m.book) AS total FROM MagicThing m GROUP BY m.book")
	List<GroupByCount<Book>> countTotalMagicThingsByBook();
}