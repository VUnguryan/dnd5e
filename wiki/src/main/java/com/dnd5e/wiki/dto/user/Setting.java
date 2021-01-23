package com.dnd5e.wiki.dto.user;

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
}