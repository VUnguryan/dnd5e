package com.dnd5e.wiki.controller.rest.model.json.foundary;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dnd5e.wiki.model.creature.Action;
import com.dnd5e.wiki.model.creature.CreatureFeat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FItemData {
	private FDiscription description;
	private String source = "";
	private int quantity = 1;
	private float weight;
	private float price;
	private boolean attuned;
	private boolean equipped = true;
	private String rarity;
	private boolean  identified = true;
	private FActivation activation;
	private FDuration duration;
	private FTarget target;
	private FRange range;
	private FUses uses;
	private FConsume consume;
	private String ability = "";
	private String actionType= ""; 
    private byte attackBonus;
    private String chatFlavor = "";
    private String critical;
    private FItemDamage damage;
    private String formula = "";
    private FSave save;
    private FArmor armor;
    private FIHP hp;
    private String weaponType = "natural";
    private FWeaponProperties properties;
    private boolean proficient = true;
    private String cptooltipmode = "hide";

	FItemData(CreatureFeat feat) {
		description = new FDiscription(feat.getDescription());
		activation = new FActivation();
		duration = new FDuration();
		target = new FTarget();
		range = new FRange();
		uses = new FUses();
		consume = new FConsume();
		save = new FSave();
		armor = new FArmor();
		hp = new FIHP();
		properties = new FWeaponProperties();
	}
	
	public FItemData(Action action) {
		description = new FDiscription(action.getDescription());
		activation = new FActivation("action", (byte) 1, "");
		duration = new FDuration();
		target = new FTarget();
		range = new FRange();
		uses = new FUses();
		consume = new FConsume();
		if (action.getDescription().contains("Рукопашная атака оружием:")) {
			actionType = "mwak"; 
		} else if (action.getDescription().contains("Дальнобойная атака оружием:")) {
			actionType = "rwak";	
		}
		
		save = new FSave();
		armor = new FArmor();
		damage = new FItemDamage();
		String damageType ="";
		if (action.getDescription().toLowerCase().contains("колющий урон")) {
			damageType = "piercing";
		} else if (action.getDescription().toLowerCase().contains("рубящий урон")) {
			damageType = "slashing";
		} else if (action.getDescription().toLowerCase().contains("дробящий урон")) {
			damageType = "bludgeoning";
		}else if (action.getDescription().contains("электрич")) {
			damageType = "lightning";
		}
		
		Pattern pattern = Pattern.compile("\\d+\\s?к\\s?\\d+");
	    Matcher matcher = pattern.matcher(action.getDescription());
	    while (matcher.find()) {
	    	String damageFormula = matcher.group().replace("к", "d");
	    	if (damage.getParts().isEmpty()) {
				damage.addDamage(damageFormula + "+@mod", damageType);
	    	}
	    	else {
	    		if (action.getDescription().contains("ядом")) {
	    			damageType = "poison";
	    		} else if (action.getDescription().contains("электрич")) {
	    			damageType = "lightning";
	    		}
	    		
	    		damage.addDamage(damageFormula + "+@mod", damageType);
	    	}
	    }
		hp = new FIHP();
		properties = new FWeaponProperties();
	}
}