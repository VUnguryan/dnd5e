package com.dnd5e.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd5e.wiki.model.travel.Transport;

public interface TransportRepository extends JpaRepository<Transport, Integer>{

}
