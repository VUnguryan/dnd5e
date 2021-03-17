package com.dnd5e.wiki.dto.user;

import java.util.EnumSet;
import java.util.Set;

import com.dnd5e.wiki.model.TypeBook;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Setting {
	private boolean baseRule = true;
	private boolean setting;
	private boolean module;
	private boolean homeRule;
	public Set<TypeBook> getTypeBooks() {
		Set<TypeBook> types = EnumSet.noneOf(TypeBook.class);
		if (baseRule) {
			types.add(TypeBook.OFFICAL);
		}
		if (setting) {
			types.add(TypeBook.SETTING);
		}
		if (module) {
			types.add(TypeBook.MODULE);
		}
		if (homeRule) {
			types.add(TypeBook.CUSTOM);
		}
		return types;
	}
}