
package com.dnd5e.wiki.builder.handler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.dnd5e.wiki.builder.model.AbilityInfo;
import com.dnd5e.wiki.builder.model.HeroModel;

@Component
public class AbilityBuilderHandler {
	public void changeMethod(HeroModel model, AbilityInfo abilityInfo) {
		AbilityInfo ai = new AbilityInfo();
		if ("array".equals(abilityInfo.getPointMethod())) {
			List<Byte> defaultList = Arrays.asList((byte) 8, (byte) 10, (byte) 12, (byte) 13, (byte) 14, (byte) 15);
			ai.setStrengthArray(new TreeSet<>(defaultList));
			ai.setDexterityArray(new TreeSet<>(defaultList));
			ai.setConstitutionArray(new TreeSet<>(defaultList));
			ai.setIntellectArray(new TreeSet<>(defaultList));
			ai.setWizdomArray(new TreeSet<>(defaultList));
			ai.setCharismaArray(new TreeSet<>(defaultList));
			abilityInfo.setStrength((byte) 0);
			abilityInfo.setDexterity((byte) 0);
			abilityInfo.setConstitution((byte) 0);
			abilityInfo.setIntellect((byte) 0);
			abilityInfo.setWizdom((byte) 0);
			abilityInfo.setCharisma((byte) 0);
		}
		if ("pointbuy".equals(abilityInfo.getPointMethod())) {
			abilityInfo.setPoints(27);
			abilityInfo.setStrength((byte) 8);
			abilityInfo.setDexterity((byte) 8);
			abilityInfo.setConstitution((byte) 8);
			abilityInfo.setIntellect((byte) 8);
			abilityInfo.setWizdom((byte) 8);
			abilityInfo.setCharisma((byte) 8);
			Map<Integer, String> template = new TreeMap<>();
			template.put(8, " 8");
			template.put(9, " 9 (-1 балл)");
			template.put(10, "10 (-2 балла)");
			template.put(11, "11 (-3 балла)");
			template.put(12, "12 (-4 балла)");
			template.put(13, "13 (-5 баллов)");
			template.put(14, "14 (-7 баллов)");
			template.put(15, "15 (-9 баллов)");
			ai.setStrengtPoints(template);
			ai.setDexterityPoints(new TreeMap<>(template));
			ai.setConstitutionPoints(new TreeMap<>(template));
			ai.setIntellectPoints(new TreeMap<>(template));
			ai.setWizdomPoints(new TreeMap<>(template));
			ai.setCharismaPoints(new TreeMap<>(template));
		}
		ai.setStrength(abilityInfo.getStrength());
		ai.setDexterity(abilityInfo.getDexterity());
		ai.setConstitution(abilityInfo.getConstitution());
		ai.setIntellect(abilityInfo.getIntellect());
		ai.setWizdom(abilityInfo.getWizdom());
		ai.setCharisma(abilityInfo.getCharisma());

		ai.setPoints(abilityInfo.getPoints());
		ai.setPointMethod(abilityInfo.getPointMethod());

		model.setAbilityInfo(ai);
	}

	public void changeAbility(HeroModel model, AbilityInfo abilityInfo) {
		if (abilityInfo.getStrength() < 3) {
			abilityInfo.setStrength((byte) 3);
		}
		if (abilityInfo.getDexterity() < 3) {
			abilityInfo.setDexterity((byte) 3);
		}
		if (abilityInfo.getConstitution() < 3) {
			abilityInfo.setConstitution((byte) 3);
		}
		if (abilityInfo.getIntellect() < 3) {
			abilityInfo.setIntellect((byte) 3);
		}
		if (abilityInfo.getWizdom() < 3) {
			abilityInfo.setWizdom((byte) 3);
		}
		if (abilityInfo.getCharisma() < 3) {
			abilityInfo.setCharisma((byte) 3);
		}

		if (abilityInfo.getStrength() > 18) {
			abilityInfo.setStrength((byte) 18);
		}
		if (abilityInfo.getDexterity() > 18) {
			abilityInfo.setDexterity((byte) 18);
		}
		if (abilityInfo.getConstitution() > 18) {
			abilityInfo.setConstitution((byte) 18);
		}
		if (abilityInfo.getIntellect() > 18) {
			model.getAbilityInfo().setIntellect((byte) 18);
		}
		if (abilityInfo.getWizdom() > 18) {
			abilityInfo.setWizdom((byte) 18);
		}
		if (abilityInfo.getCharisma() > 18) {
			model.getAbilityInfo().setCharisma((byte) 18);
		}
		model.getAbilityInfo().setStrength(abilityInfo.getStrength());
		model.getAbilityInfo().setDexterity(abilityInfo.getDexterity());
		model.getAbilityInfo().setConstitution(abilityInfo.getConstitution());
		model.getAbilityInfo().setIntellect(abilityInfo.getIntellect());
		model.getAbilityInfo().setWizdom(abilityInfo.getWizdom());
		model.getAbilityInfo().setCharisma(abilityInfo.getCharisma());
	}

	public void changeAbilityArray(HeroModel model, AbilityInfo abilityInfo) {
		if (model.getAbilityInfo().getStrength() != abilityInfo.getStrength()) {
			byte newValue = abilityInfo.getStrength();
			model.getAbilityInfo().getDexterityArray().remove(newValue);
			model.getAbilityInfo().getConstitutionArray().remove(newValue);
			model.getAbilityInfo().getIntellectArray().remove(newValue);
			model.getAbilityInfo().getWizdomArray().remove(newValue);
			model.getAbilityInfo().getCharismaArray().remove(newValue);

			byte oldValue = model.getAbilityInfo().getStrength();
			if (oldValue != 0) {
				model.getAbilityInfo().getDexterityArray().add(oldValue);
				model.getAbilityInfo().getConstitutionArray().add(oldValue);
				model.getAbilityInfo().getIntellectArray().add(oldValue);
				model.getAbilityInfo().getWizdomArray().add(oldValue);
				model.getAbilityInfo().getCharismaArray().add(oldValue);
			}

			model.getAbilityInfo().setStrength(abilityInfo.getStrength());
		}
		if (model.getAbilityInfo().getDexterity() != abilityInfo.getDexterity()) {
			byte newValue = abilityInfo.getDexterity();
			model.getAbilityInfo().getStrengthArray().remove(newValue);
			model.getAbilityInfo().getConstitutionArray().remove(newValue);
			model.getAbilityInfo().getIntellectArray().remove(newValue);
			model.getAbilityInfo().getWizdomArray().remove(newValue);
			model.getAbilityInfo().getCharismaArray().remove(newValue);

			byte oldValue = model.getAbilityInfo().getDexterity();
			if (oldValue != 0) {
				model.getAbilityInfo().getStrengthArray().add(oldValue);
				model.getAbilityInfo().getConstitutionArray().add(oldValue);
				model.getAbilityInfo().getIntellectArray().add(oldValue);
				model.getAbilityInfo().getWizdomArray().add(oldValue);
				model.getAbilityInfo().getCharismaArray().add(oldValue);
			}

			model.getAbilityInfo().setDexterity(newValue);
		}
		if (model.getAbilityInfo().getConstitution() != abilityInfo.getConstitution()) {
			byte newValue = abilityInfo.getConstitution();
			model.getAbilityInfo().getStrengthArray().remove(newValue);
			model.getAbilityInfo().getDexterityArray().remove(newValue);
			model.getAbilityInfo().getIntellectArray().remove(newValue);
			model.getAbilityInfo().getWizdomArray().remove(newValue);
			model.getAbilityInfo().getCharismaArray().remove(newValue);

			byte oldValue = model.getAbilityInfo().getConstitution();
			if (oldValue != 0) {
				model.getAbilityInfo().getStrengthArray().add(oldValue);
				model.getAbilityInfo().getDexterityArray().add(oldValue);
				model.getAbilityInfo().getIntellectArray().add(oldValue);
				model.getAbilityInfo().getWizdomArray().add(oldValue);
				model.getAbilityInfo().getCharismaArray().add(oldValue);
			}
			model.getAbilityInfo().setConstitution(newValue);
		}
		if (model.getAbilityInfo().getIntellect() != abilityInfo.getIntellect()) {
			byte newValue = abilityInfo.getIntellect();
			model.getAbilityInfo().getStrengthArray().remove(newValue);
			model.getAbilityInfo().getDexterityArray().remove(newValue);
			model.getAbilityInfo().getConstitutionArray().remove(newValue);
			model.getAbilityInfo().getWizdomArray().remove(newValue);
			model.getAbilityInfo().getCharismaArray().remove(newValue);

			byte oldValue = model.getAbilityInfo().getIntellect();
			if (oldValue != 0) {
				model.getAbilityInfo().getStrengthArray().add(oldValue);
				model.getAbilityInfo().getDexterityArray().add(oldValue);
				model.getAbilityInfo().getConstitutionArray().add(oldValue);
				model.getAbilityInfo().getWizdomArray().add(oldValue);
				model.getAbilityInfo().getCharismaArray().add(oldValue);
			}
			model.getAbilityInfo().setIntellect(newValue);
		}
		if (model.getAbilityInfo().getWizdom() != abilityInfo.getWizdom()) {
			byte newValue = abilityInfo.getWizdom();
			model.getAbilityInfo().getStrengthArray().remove(newValue);
			model.getAbilityInfo().getDexterityArray().remove(newValue);
			model.getAbilityInfo().getConstitutionArray().remove(newValue);
			model.getAbilityInfo().getIntellectArray().remove(newValue);
			model.getAbilityInfo().getCharismaArray().remove(newValue);

			byte oldValue = model.getAbilityInfo().getWizdom();
			if (oldValue != 0) {
				model.getAbilityInfo().getStrengthArray().add(oldValue);
				model.getAbilityInfo().getDexterityArray().add(oldValue);
				model.getAbilityInfo().getConstitutionArray().add(oldValue);
				model.getAbilityInfo().getIntellectArray().add(oldValue);
				model.getAbilityInfo().getCharismaArray().add(oldValue);
			}
			model.getAbilityInfo().setWizdom(newValue);
		}
		if (model.getAbilityInfo().getCharisma() != abilityInfo.getCharisma()) {
			byte newValue = abilityInfo.getCharisma();
			model.getAbilityInfo().getStrengthArray().remove(newValue);
			model.getAbilityInfo().getDexterityArray().remove(newValue);
			model.getAbilityInfo().getConstitutionArray().remove(newValue);
			model.getAbilityInfo().getIntellectArray().remove(newValue);
			model.getAbilityInfo().getWizdomArray().remove(newValue);

			byte oldValue = model.getAbilityInfo().getCharisma();
			if (oldValue != 0) {
				model.getAbilityInfo().getStrengthArray().add(oldValue);
				model.getAbilityInfo().getDexterityArray().add(oldValue);
				model.getAbilityInfo().getConstitutionArray().add(oldValue);
				model.getAbilityInfo().getIntellectArray().add(oldValue);
				model.getAbilityInfo().getWizdomArray().add(oldValue);
			}
			model.getAbilityInfo().setCharisma(newValue);
		}
	}

	public void changePointBy(HeroModel model, AbilityInfo abilityInfo) {
		int currentPoints = model.getAbilityInfo().getPoints();
		if (model.getAbilityInfo().getStrength() != abilityInfo.getStrength()) {
			byte newValue = abilityInfo.getStrength();
			byte oldValue = model.getAbilityInfo().getStrength();
			model.getAbilityInfo().setPoints(calculateNewPoints(currentPoints, newValue, oldValue));
			model.getAbilityInfo().setStrengtPoints(calculateLabelPoints(newValue));
			model.getAbilityInfo().setStrength(abilityInfo.getStrength());
		}
		if (model.getAbilityInfo().getDexterity() != abilityInfo.getDexterity()) {
			byte newValue = abilityInfo.getDexterity();
			byte oldValue = model.getAbilityInfo().getDexterity();
			model.getAbilityInfo().setPoints(calculateNewPoints(currentPoints, newValue, oldValue));
			model.getAbilityInfo().setDexterityPoints(calculateLabelPoints(newValue));
			model.getAbilityInfo().setDexterity(newValue);
		}
		if (model.getAbilityInfo().getConstitution() != abilityInfo.getConstitution()) {
			byte newValue = abilityInfo.getConstitution();
			byte oldValue = model.getAbilityInfo().getConstitution();
			model.getAbilityInfo().setPoints(calculateNewPoints(currentPoints, newValue, oldValue));
			model.getAbilityInfo().setConstitutionPoints(calculateLabelPoints(newValue));
			model.getAbilityInfo().setConstitution(newValue);
		}
		if (model.getAbilityInfo().getIntellect() != abilityInfo.getIntellect()) {
			byte newValue = abilityInfo.getIntellect();
			byte oldValue = model.getAbilityInfo().getIntellect();
			model.getAbilityInfo().setPoints(calculateNewPoints(currentPoints, newValue, oldValue));
			model.getAbilityInfo().setIntellectPoints(calculateLabelPoints(newValue));
			model.getAbilityInfo().setIntellect(newValue);
		}
		if (model.getAbilityInfo().getWizdom() != abilityInfo.getWizdom()) {
			byte newValue = abilityInfo.getWizdom();
			byte oldValue = model.getAbilityInfo().getWizdom();
			model.getAbilityInfo().setPoints(calculateNewPoints(currentPoints, newValue, oldValue));
			model.getAbilityInfo().setWizdomPoints(calculateLabelPoints(newValue));
			model.getAbilityInfo().setWizdom(newValue);
		}
		if (model.getAbilityInfo().getCharisma() != abilityInfo.getCharisma()) {
			byte newValue = abilityInfo.getCharisma();
			byte oldValue = model.getAbilityInfo().getCharisma();
			model.getAbilityInfo().setPoints(calculateNewPoints(currentPoints, newValue, oldValue));
			model.getAbilityInfo().setCharismaPoints(calculateLabelPoints(newValue));
			model.getAbilityInfo().setCharisma(newValue);
		}
	}

	private int calculateNewPoints(int currentPoints, int newValue, int oldValue) {
		if (newValue > oldValue) {
			if (oldValue != 14 && newValue > 13) {
				currentPoints--;
			}
			if (newValue > 14) {
				currentPoints--;
			}
			return currentPoints - (newValue - oldValue);
		} else {
			if (newValue != 14 && oldValue > 13) {
				currentPoints++;
			}
			if (oldValue > 14) {
				currentPoints++;
			}
			return currentPoints + (oldValue - newValue);
		}
	}
	
	private Map<Integer, String> calculateLabelPoints(int newValue) {
		Map<Integer, String> template = new TreeMap<>();
		switch (newValue) {
			case 8:
				template.put(8, "8");
				template.put(9, "9 (-1 балл)");
				template.put(10, "10 (-2 балла)");
				template.put(11, "11 (-3 балла)");
				template.put(12, "12 (-4 балла)");
				template.put(13, "13 (-5 баллов)");
				template.put(14, "14 (-7 баллов)");
				template.put(15, "15 (-9 баллов)");
				break;
			case 9:
				template.put(8, "8 (+1 балл)");
				template.put(9, "9");
				template.put(10, "10 (-1 балла)");
				template.put(11, "11 (-2 балла)");
				template.put(12, "12 (-3 балла)");
				template.put(13, "13 (-4 баллов)");
				template.put(14, "14 (-6 баллов)");
				template.put(15, "15 (-8 баллов)");
				break;
			case 10:
				template.put(8, "8 (+2 балла)");
				template.put(9, "9 (+1 балл)");
				template.put(10, "10");
				template.put(11, "11 (-1 балл)");
				template.put(12, "12 (-2 балла)");
				template.put(13, "13 (-3 балла)");
				template.put(14, "14 (-5 баллов)");
				template.put(15, "15 (-7 баллов)");
				break;
			case 11:
				template.put(8, "8 (+3 балла)");
				template.put(9, "9 (+2 балла)");
				template.put(10, "10 (+1 балл)");
				template.put(11, "11");
				template.put(12, "12 (-1 балл)");
				template.put(13, "13 (-2 балла)");
				template.put(14, "14 (-4 балла)");
				template.put(15, "15 (-6 баллов)");
				break;
			case 12:
				template.put(8, "8 (+4 балла)");
				template.put(9, "9 (+3 балла)");
				template.put(10, "10 (+2 балла)");
				template.put(11, "11 (+1 балл)");
				template.put(12, "12");
				template.put(13, "13 (-1 балл)");
				template.put(14, "14 (-3 балла)");
				template.put(15, "15 (-5 баллов)");
				break;
			case 13:
				template.put(8, "8 (+5 баллов)");
				template.put(9, "9 (+4 балла)");
				template.put(10, "10 (+3 балла)");
				template.put(11, "11 (+2 балла)");
				template.put(12, "12 (+1 балл)");
				template.put(13, "13");
				template.put(14, "14 (-2 балла)");
				template.put(15, "15 (-4 балла)");
				break;
			case 14:
				template.put(8, "8 (+7 баллов)");
				template.put(9, "9 (+6 баллов)");
				template.put(10, "10 (+5 балла)");
				template.put(11, "11 (+4 балла)");
				template.put(12, "12 (+3 балла)");
				template.put(13, "13 (+2 балла)");
				template.put(14, "14");
				template.put(15, "15 (-2 балла)");
				break;
			case 15:
				template.put(8, "8 (+9 баллов)");
				template.put(9, "9 (+8 баллов)");
				template.put(10, "10 (+7 баллов)");
				template.put(11, "11 (+6 баллов)");
				template.put(12, "12 (+5 баллов)");
				template.put(13, "13 (+4 баллов)");
				template.put(14, "14 (+2 баллов)");
				template.put(15, "15");
				break;
		}
		return template;
	}
}