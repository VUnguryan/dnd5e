package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer>{

}
