package com.dnd5e.wiki.controller.rest.paging;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SpellOption {
	private List<Item> level;
}