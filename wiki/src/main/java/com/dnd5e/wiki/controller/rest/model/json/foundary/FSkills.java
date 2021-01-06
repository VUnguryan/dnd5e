package com.dnd5e.wiki.controller.rest.model.json.foundary;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.dnd5e.wiki.model.creature.Skill;
import com.dnd5e.wiki.model.creature.SkillType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FSkills {
	private FSkill acr;
	private FSkill arc;
	private FSkill ath;
	private FSkill dec;
	private FSkill his;
	private FSkill ins;
	private FSkill itm;
	private FSkill inv;
	private FSkill med;
	private FSkill nat;
	private FSkill prc;
	private FSkill prf;
	private FSkill per;
	private FSkill rel;
	private FSkill slt;
	private FSkill ste;
	private FSkill sur;
	public FSkills(List<Skill> skills) {
		Map<SkillType, Byte> skillMap = skills.stream().collect(Collectors.toMap(Skill::getType, skill -> skill.getBonus()));
		acr = new FSkill();
		if (skillMap.containsKey(SkillType.ACROBATICS)) {
			acr.setValue((byte)1);
			acr.setMod(skillMap.get(SkillType.ACROBATICS));
		}
		acr.setAbility("dex");
		
		arc = new FSkill();
		if (skillMap.containsKey(SkillType.ARCANA)) {
			arc.setValue((byte)1);
			arc.setMod(skillMap.get(SkillType.ARCANA));
		}
		arc.setAbility("int");
		
		ath = new FSkill();
		if (skillMap.containsKey(SkillType.ATHLETICS)) {
			ath.setValue((byte)1);
			ath.setMod(skillMap.get(SkillType.ATHLETICS));
		}
		ath.setAbility("str");
		
		dec = new FSkill();
		if (skillMap.containsKey(SkillType.DECEPTION)) {
			dec.setValue((byte)1);
			dec.setMod(skillMap.get(SkillType.DECEPTION));
		}
		dec.setAbility("cha");
		
		his= new FSkill();
		if (skillMap.containsKey(SkillType.HISTORY)) {
			his.setValue((byte)1);
			//his.setValue(skillMap.get(SkillType.HISTORY));
		}
		his.setAbility("int");
		
		ins = new FSkill();
		if (skillMap.containsKey(SkillType.INSIGHT)) {
			ins.setValue((byte)1);
			ins.setMod(skillMap.get(SkillType.INSIGHT));
		}
		ins.setAbility("wis");
		
		itm = new FSkill();
		if (skillMap.containsKey(SkillType.INTIMIDATION)) {
			itm.setValue((byte)1);
			itm.setMod(skillMap.get(SkillType.INTIMIDATION));
		}
		itm.setAbility("cha");
		
		inv = new FSkill();
		if (skillMap.containsKey(SkillType.INVESTIGATION)) {
			inv.setValue((byte)1);
			inv.setMod(skillMap.get(SkillType.INVESTIGATION));
		}
		inv.setAbility("int");
		
		med = new FSkill();
		if (skillMap.containsKey(SkillType.MEDICINE)) {
			med.setValue((byte)1);
			med.setMod(skillMap.get(SkillType.MEDICINE));
		}
		med.setAbility("wis");
		
		nat = new FSkill();
		if (skillMap.containsKey(SkillType.NATURE)) {
			nat.setValue((byte)1);
			nat.setMod(skillMap.get(SkillType.NATURE));
		}
		nat.setAbility("int");
		
		prc = new FSkill();
		if (skillMap.containsKey(SkillType.PERSUASION)) {
			prc.setValue((byte)1);
			prc.setMod(skillMap.get(SkillType.PERSUASION));
		}
		prc.setAbility("int");
		
		prf = new FSkill();
		if (skillMap.containsKey(SkillType.PERFORMANCE)) {
			prf.setValue((byte)1);
			prf.setValue(skillMap.get(SkillType.PERFORMANCE));
		}
		prf.setAbility("int");
		
		per = new FSkill();
		if (skillMap.containsKey(SkillType.PERCEPTION)) {
			per.setValue((byte)1);
			per.setMod(skillMap.get(SkillType.PERCEPTION));
		}
		per.setAbility("int");
		
		rel = new FSkill();
		if (skillMap.containsKey(SkillType.RELIGION)) {
			rel.setValue((byte)1);
			rel.setMod(skillMap.get(SkillType.RELIGION));
		}
		rel.setAbility("int");
		
		slt = new FSkill();
		if (skillMap.containsKey(SkillType.SLEIGHT_OF_HAND)) {
			slt.setValue((byte)1);
			slt.setMod(skillMap.get(SkillType.SLEIGHT_OF_HAND));
		}
		slt.setAbility("int");
		
		ste = new FSkill();
		if (skillMap.containsKey(SkillType.STEALTH)) {
			ste.setValue((byte)1);
			ste.setMod(skillMap.get(SkillType.STEALTH));
		}
		ste.setAbility("int");
		
		sur = new FSkill();
		if (skillMap.containsKey(SkillType.SURVIVAL)) {
			sur.setValue((byte)1);
			sur.setMod(skillMap.get(SkillType.SURVIVAL));
		}
		sur.setAbility("wis");
	}
}