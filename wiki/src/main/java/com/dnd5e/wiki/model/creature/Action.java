package com.dnd5e.wiki.model.creature;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="action")
@Data
public class Action
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;
  private String name;
  
  @Column(columnDefinition = "TEXT")
  private String description;
  
  @Enumerated(EnumType.ORDINAL)
  private ActionType actionType;
}