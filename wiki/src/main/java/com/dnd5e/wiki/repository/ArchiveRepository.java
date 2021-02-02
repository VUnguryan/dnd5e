package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.Archive;

public interface ArchiveRepository extends JpaRepository<Archive, Integer> {

}
