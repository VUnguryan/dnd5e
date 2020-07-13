package com.dnd5e.wiki.controller.rest.model.json.shaped;

import com.dnd5e.wiki.controller.rest.model.xml.Compendium;
import com.dnd5e.wiki.model.creature.ActionType;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class Action {
	private String name;
	private String text;
	private String recharge;
	private Byte cost;
	public Action(com.dnd5e.wiki.model.creature.Action action) {
		name = action.getName();
		text = Compendium.removeHtml(action.getDescription().replace("</p>","\n"));
		if (name.contains("(1/ход)")) {
			recharge = "1/Turn";
			name = name.replace("(1/ход)", "").trim();
		}
		if (name.contains("(1/день)")) {
			recharge = "1/Day";
			name = name.replace("(1/день)", "").trim();
		}
		if (name.contains("(2/день)")) {
			recharge = "2/Day";
			name = name.replace("(2/день)", "").trim();
		}
		if (name.contains("(3/день)")) {
			recharge = "3/Day";
			name = name.replace("(3/день)", "").trim();
		}
		if (name.contains("(4/день)")) {
			recharge = "4/Day";
			name = name.replace("(4/день)", "").trim();
		}
		if (name.contains("(5/день)")) {
			recharge = "5/Day";
			name = name.replace("(5/день)", "").trim();
		}
		if (name.contains("(перезарядка 4–6)")) {
			recharge = "Recharge 4-6";
			name = name.replace("(перезарядка 4–6)", "").trim();
		}
		if (name.contains("(перезарядка 4-6)")) {
			recharge = "Recharge 4-6";
			name = name.replace("(перезарядка 4-6)", "").trim();
		}
		if (name.contains("(перезарядка 5–6)")) {
			recharge = "Recharge 5-6";
			name = name.replace("(перезарядка 5–6)", "").trim();
		}
		if (name.contains("(перезарядка 5-6)")) {
			recharge = "Recharge 5-6";
			name = name.replace("(перезарядка 5-6)", "").trim();
		}
		if (name.contains("(перезарядка 6)")) {
			recharge = "Recharge 6";
			name = name.replace("(перезарядка 6)", "").trim();
		}

		if (name.contains("(перезарядка после короткого или продолжительного отдыха)")) {
			recharge = "Recharges after a Short or Long Rest";
			name = name.replace("(перезарядка после короткого или продолжительного отдыха)", "").trim();
		}
		if (name.contains("(перезарядка после продолжительного отдыха)")) {
			recharge = "Recharges after a Long Rest";
			name = name.replace("(перезарядка после продолжительного отдыха)", "").trim();
		}
		if (action.getActionType()== ActionType.LEGENDARY) {
			cost = 1;
			if (name.contains("(2 действия)")) {
				name = name.replace("(2 действия)", "").trim();
				cost = 2;
			}
			if (name.contains("(стоит 2 действия)")) {
				name = name.replace("(стоит 2 действия)", "").trim();
				cost = 2;
			}
			if (name.contains("(3 действия)")) {
				name = name.replace("(3 действия)", "").trim();
				cost = 3;
			}
			if (name.contains("(стоит 3 действия)")) {
				name = name.replace("(3 действия)", "").trim();
				cost = 3;
			}
			recharge = null;
		}
	}
}