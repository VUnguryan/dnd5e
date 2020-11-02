package com.dnd5e.wiki.builder.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeroInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private byte[] imageContent;
}