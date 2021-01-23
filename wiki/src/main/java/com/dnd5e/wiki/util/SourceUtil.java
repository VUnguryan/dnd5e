package com.dnd5e.wiki.util;

import java.util.EnumSet;
import java.util.Set;

import com.dnd5e.wiki.dto.user.Setting;
import com.dnd5e.wiki.model.TypeBook;

public class SourceUtil {
	public static Set<TypeBook> getSources(Setting settings){
		Set<TypeBook> types = EnumSet.noneOf(TypeBook.class);
		if (settings == null) {
			types.add(TypeBook.OFFICAL);
		}
		else {
			if (settings.isBaseRule()) {
				types.add(TypeBook.OFFICAL);
			}
			if (settings.isModule()) {
				types.add(TypeBook.MODULE);
			}
			if (settings.isSetting()) {
				types.add(TypeBook.SETTING);
			}
			if (settings.isHomeRule()) {
				types.add(TypeBook.CUSTOM);
			}
		}
		return types;
	}
}
