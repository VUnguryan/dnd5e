package com.dnd5e.wiki.model.workshop.magic.book;

import java.sql.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dnd5e.wiki.model.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

//@Entity
@Table(name = "magic_books")
public class MagicBook {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	private Date createDate; 
}