package com.dnd5e.wiki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dnd5e.wiki.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
	@Query(value = "SELECT distinct b.source, b.description, b.name, b.type FROM books b RIGHT JOIN spells s ON b.source = s.source", nativeQuery = true)
	List<Book> finadAllByLeftJoinSpell();

	@Query(value = "SELECT distinct b.source, b.description, b.name, b.type FROM books b RIGHT JOIN creatures c ON b.source = c.source", nativeQuery = true)
	List<Book> finadAllByLeftJoinCreature();

	@Query(value = "SELECT distinct b.source, b.description, b.name, b.type FROM books b JOIN artifactes c ON b.source = c.source", nativeQuery = true)
	List<Book> finadAllByLeftJoinMagicThings();

	@Query(value = "SELECT distinct b.source, b.description, b.name, b.type FROM books b JOIN traits c ON b.source = c.source", nativeQuery = true)
	List<Book> finadAllByLeftJoinHeroTrait();
}