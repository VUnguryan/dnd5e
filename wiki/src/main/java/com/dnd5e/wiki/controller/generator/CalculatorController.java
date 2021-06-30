package com.dnd5e.wiki.controller.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dnd5e.wiki.dto.MagicThingDto;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.creature.Alignment;
import com.dnd5e.wiki.model.creature.Creature;
import com.dnd5e.wiki.model.creature.HabitatType;
import com.dnd5e.wiki.model.encounter.RandomCreature;
import com.dnd5e.wiki.model.encounter.RandomCreatureDto;
import com.dnd5e.wiki.model.encounter.RandomEncounter;
import com.dnd5e.wiki.model.hero.LifeStyle;
import com.dnd5e.wiki.model.hero.madness.Madness;
import com.dnd5e.wiki.model.hero.madness.MadnessType;
import com.dnd5e.wiki.model.spell.Spell;
import com.dnd5e.wiki.model.spell.WildMagic;
import com.dnd5e.wiki.model.stock.Weapon;
import com.dnd5e.wiki.model.treasure.MagicThingTable;
import com.dnd5e.wiki.model.treasure.Rarity;
import com.dnd5e.wiki.model.treasure.Treasure;
import com.dnd5e.wiki.model.treasure.TreasureType;
import com.dnd5e.wiki.repository.MadnessRepository;
import com.dnd5e.wiki.repository.MagicThingTableRepository;
import com.dnd5e.wiki.repository.RandomEncounterRepository;
import com.dnd5e.wiki.repository.SpellRepository;
import com.dnd5e.wiki.repository.TreasureRepository;
import com.dnd5e.wiki.repository.WeaponRepository;
import com.dnd5e.wiki.repository.WildMagicRepository;

@Controller
@RequestMapping("/calc")
public class CalculatorController {
	public static final Random rnd = new Random();
	@Autowired
	private WildMagicRepository wildMagicRepo;
	@Autowired
	private MadnessRepository madnessRepo;
	@Autowired
	private MagicThingTableRepository mtRepo;
	@Autowired
	private SpellRepository spellRepo;
	@Autowired
	private WeaponRepository weaponRepo;
	@Autowired
	private TreasureRepository treasureRepo;
	@Autowired
	private RandomEncounterRepository encounterRepo;

	@GetMapping("/master")
	public String getMaster(Model model) {
		return "master";
	}

	@GetMapping("/idle")
	public String getIdleForm(Model model) {
		model.addAttribute("lifeStyles", LifeStyle.values());
		model.addAttribute("costs", new float[] { 0, 0.1f, 0.2f, 1, 2, 4, 10 });
		return "idle";
	}

	@GetMapping("/buyMagicThings")
	public String getBuyMagicThingsForm(Model model) {
		model.addAttribute("costs", new float[] { 0, 0.1f, 0.2f, 1, 2, 4, 10 });
		model.addAttribute("rarities", Rarity.values());
		return "buyMagicThings";
	}

	@GetMapping("/wildMagic")
	public String getWildMagicRandom(Model model) {
		return "calc/wildMagic";
	}
	
	@GetMapping("/wildMagicText")
	@ResponseBody
	public String getWildMagicRandomText(Model model) {
		List<WildMagic> magics = wildMagicRepo.findAll();
		if (!magics.isEmpty()) {
			return magics.get(rnd.nextInt(magics.size())).getDescription();
		}
		return "Нет дикой магии сегодня";
	}

	@GetMapping("/madness")
	public String madness(Model model, String typeMadness) {
		List<Madness> madnesses;
		if (typeMadness != null) {
			MadnessType madnessType = MadnessType.valueOf(typeMadness);
			madnesses = madnessRepo.findByMadnessType(madnessType);
		} else {
			madnesses = madnessRepo.findAll();
		}
		if (!madnesses.isEmpty()) {
			model.addAttribute("types", MadnessType.values());
			Madness madness = madnesses.get(rnd.nextInt(madnesses.size()));
			model.addAttribute("selectedType", madness.getMadnessType().getCyrilicName());
			model.addAttribute("madness", madness);
		}
		return "calc/madness";
	}

	@GetMapping("/trader")
	public String getTraiderForm(Model model) {
		model.addAttribute("result", 1);
		model.addAttribute("mageRang", 2);
		return "calc/trader";
	}

	@GetMapping("/encounters")
	public String getRandomEncountersForm(Model model) {
		model.addAttribute("types", HabitatType.types());
		return "calc/encounters";
	}

	@PostMapping("/encounters")
	public String getRandomEncounters(Integer start, Integer end, Integer level, HabitatType type, Model model) {
		List<RandomEncounter> encounters = encounterRepo.findAllByLevelAndType(level, type);
		model.addAttribute("encounters", encounters);

		int index = 1 + rnd.nextInt(100);
		RandomEncounter encounter = encounterRepo.findOne(index, level, type);
		if (encounter != null && encounter.getCreatures() != null) {
			List<RandomCreatureDto> creatures = new ArrayList<>();
			for (RandomCreature creature : encounter.getCreatures()) {
				RandomCreatureDto rcreature = new RandomCreatureDto();
				rcreature.setCreature(creature.getCreature());
				int count = creature.getCount();
				if (creature.getDice() != null) {
					count += getCountDice(creature.getCountDice(), creature.getDice().getMaxValue());
					rcreature.setCount(count);
				} else {
					rcreature.setCount(count);
				}
				creatures.add(rcreature);
			}
			model.addAttribute("creatures", creatures);
		}
		model.addAttribute("encounter", encounter);
		model.addAttribute("types", HabitatType.types());
		model.addAttribute("level", level);
		model.addAttribute("selectedType", type);
		return "calc/encounters";
	}

	@PostMapping("/trader")
	public String generateTraider(Model model, Integer result, Integer mageRang) {
		int coef = 0;
		if (mageRang == 1) {
			coef = -10;
		} else if (mageRang == 3) {
			coef = 10;
		}
		List<MagicThingDto> list = new ArrayList<>();
		list.addAll(getMagicThings(result + coef, 1, 5, "А", 6));
		list.addAll(getMagicThings(result + coef, 6, 10, "Б", 4));
		list.addAll(getMagicThings(result + coef, 11, 15, "В", 4));
		list.addAll(getMagicThings(result + coef, 16, 20, "Г", 4));
		list.addAll(getMagicThings(result + coef, 21, 25, "Д", 4));
		list.addAll(getMagicThings(result + coef, 26, 30, "Е", 4));
		list.addAll(getMagicThings(result + coef, 31, 35, "Е1", 4));
		list.addAll(getMagicThings(result + coef, 36, 40, "Ж", 4));
		list.addAll(getMagicThings(result + coef, 41, 1000, "З", 4));
		model.addAttribute("things", list);
		model.addAttribute("result", result);
		model.addAttribute("mageRang", mageRang);
		Collections.sort(list, Comparator.comparing(MagicThingDto::getCost));
		return "calc/trader";
	}

	private List<MagicThingDto> getMagicThings(Integer result, int start, int end, String tableName, int count) {
		if (result == null) {
			result = 1;
		}
		List<MagicThingDto> list = new ArrayList<>();
		if (result >= start) {
			for (int i = 0; i < 1 + rnd.nextInt(count); i++) {
				int ri = 1 + rnd.nextInt(100);
				// System.out.println("table= " + tableName + " in " + ri);
				MagicThingTable mt = mtRepo.findOne(ri, tableName);
				if (mt != null) {
					MagicThingDto dto = new MagicThingDto(mt.getMagicThing());
					dto.setCost(getCost(mt.getMagicThing().getRarity()));
					if (tableName.equals("Б")) {
						if (ri == 91) {
							int zap = 4 + rnd.nextInt(4) + rnd.nextInt(4) + rnd.nextInt(4) + rnd.nextInt(4);
							dto.setName(dto.getName() + "(дополнительных заплаток " + zap + ")");
						}
					}
					if (tableName.equals("В")) {
						if (ri >= 82 && ri <= 84) {
							String effect = "";
							int er = 1 + rnd.nextInt(100);
							if (er <= 15) {
								effect = "веер";
							} else if (er <= 40) {
								effect = "дерево";
							} else if (er <= 50) {
								effect = "кнут";
							} else if (er <= 65) {
								effect = "лодка-лебедь";
							} else if (er <= 80) {
								effect = "птица";
							} else {
								effect = "якорь";
							}
							dto.setName(dto.getName() + " (Эффект: " + effect + ")");
						} else if (ri >= 85 && ri <= 87) {
							String effect = "";
							int er = 1 + rnd.nextInt(100);
							if (er <= 10) {
								effect = "Аберрации";
							} else if (er <= 20) {
								effect = "Звери";
							} else if (er <= 45) {
								effect = "Исчадия";
							} else if (er <= 55) {
								effect = "Небожителиь";
							} else if (er <= 75) {
								effect = "Нежить";
							} else if (er <= 80) {
								effect = "Растения";
							} else if (er <= 90) {
								effect = "Феи";
							} else if (er <= 100) {
								effect = "Элементали";
							} else {
								effect = "якорь";
							}
							dto.setName(dto.getName() + " (Вид существ: " + effect + ")");
						} else if (ri >= 88 && ri <= 89) {
							dto.setName(dto.getName() + " (Бобов: "
									+ (3 + rnd.nextInt(4) + rnd.nextInt(4) + rnd.nextInt(4)) + ")");
						}
					}
					if (tableName.equals("E")) {
						switch (ri) {
						case 66:
							dto.setName("Адамантиновый Доспех (кольчуга)");
							dto.setCost(Integer.valueOf(dto.getCost()) + 75);
							break;
						case 67:
							dto.setName("Адамантиновый Доспех (кольчужная рубаха)");
							dto.setCost(Integer.valueOf(dto.getCost()) + 50);
							break;
						case 68:
							dto.setName("Адамантиновый Доспех (чещуйчатый доспех)");
							dto.setCost(Integer.valueOf(dto.getCost()) + 50);
							break;
						}
					}
					if (tableName.equals("E1")) {
						switch (ri) {
						case 15:
							dto.setName("Адамантиновый Доспех (кираса)");
							dto.setCost(Integer.valueOf(dto.getCost()) + 400);
							break;
						case 16:
							dto.setName("Адамантиновый Доспех (наборной доспех)");
							dto.setCost(Integer.valueOf(dto.getCost()) + 200);
							break;
						case 35:
							dto.setName("Доспех +1 (кожаный)");
							dto.setRarity(Rarity.UNCOMMON.getCyrilicName());
							dto.setCost(getCost(Rarity.UNCOMMON) + 10);
							break;
						case 36:
							dto.setName("Доспех +1 (кольчуга)");
							dto.setRarity(Rarity.UNCOMMON.getCyrilicName());
							dto.setCost(getCost(Rarity.UNCOMMON) + 75);
							break;
						case 37:
							dto.setName("Доспех +1 (кольчужная рубаха)");
							dto.setRarity(Rarity.UNCOMMON.getCyrilicName());
							dto.setCost(getCost(Rarity.UNCOMMON) + 50);
							break;
						case 38:
							dto.setName("Доспех +1 (чешуйчатый)");
							dto.setRarity(Rarity.UNCOMMON.getCyrilicName());
							dto.setCost(getCost(Rarity.UNCOMMON) + 50);
							break;
						case 60:
							dto.setName(dto.getName() + " " + getResistenceType());
							break;
						}
					}
					if (tableName.equals("Ж")) {
						switch (ri) {
						case 55:
							dto.setName("Адамантиновый Доспех (латы)");
							dto.setCost(Integer.valueOf(dto.getCost()) + 1500);
							break;
						case 56:
							dto.setName("Адамантиновый Доспех (полулаты)");
							dto.setCost(Integer.valueOf(dto.getCost()) + 750);
							break;
						case 65:
							dto.setName("Доспех +1 (кираса)");
							dto.setRarity(Rarity.UNCOMMON.getCyrilicName());
							dto.setCost(getCost(Rarity.UNCOMMON) + 400);
							break;
						case 66:
							dto.setName("Доспех +1 (кираса)");
							dto.setRarity(Rarity.UNCOMMON.getCyrilicName());
							dto.setCost(getCost(Rarity.UNCOMMON) + 400);
							break;
						case 67:
							dto.setName("Доспех +1 (проклёпанная кожа)");
							dto.setRarity(Rarity.UNCOMMON.getCyrilicName());
							dto.setCost(getCost(Rarity.UNCOMMON) + 400);
							break;
						case 68:
							dto.setName("Доспех +2 (кожаный)");
							dto.setRarity(Rarity.RARE.getCyrilicName());
							dto.setCost(getCost(Rarity.RARE) + 10);
							break;
						case 69:
							dto.setName("Доспех +2 (кольчуга)");
							dto.setRarity(Rarity.RARE.getCyrilicName());
							dto.setCost(getCost(Rarity.RARE) + 75);
							break;
						case 70:
							dto.setName("Доспех +2 (кольчужная рубаха)");
							dto.setRarity(Rarity.RARE.getCyrilicName());
							dto.setCost(getCost(Rarity.RARE) + 50);
							break;
						case 71:
							dto.setName("Доспех +2 (чешуйчатый)");
							dto.setRarity(Rarity.RARE.getCyrilicName());
							dto.setCost(getCost(Rarity.RARE) + 50);
							break;
						case 89:
							String aligment = Alignment.values()[rnd.nextInt(Alignment.values().length)]
									.getCyrilicName();
							dto.setName(dto.getName() + "(Мировоззрение: " + aligment + ")");
							break;
						case 91:
							int rg = 1 + rnd.nextInt(20);
							String golemType = "";
							if (rg >= 1 && rg <= 5)
								golemType = "Глинянный";
							else if (rg == 6)
								golemType = "Железный";
							else if (rg >= 7 && rg <= 8)
								golemType = "Каменный";
							else if (rg >= 9 && rg <= 20)
								golemType = "Мясной (из плоти)";
							dto.setName(dto.getName() + "(" + golemType + ")");
							break;
						}
					}
					if (tableName.equals("З")) {
						switch (ri) {
						case 42:
						case 43:
							dto.setName("Доспех +1 (латы)");
							dto.setRarity(Rarity.UNCOMMON.getCyrilicName());
							dto.setCost(getCost(Rarity.UNCOMMON) + 1500);
							break;
						case 44:
						case 45:
							dto.setName("Доспех +1 (полулаты)");
							dto.setRarity(Rarity.UNCOMMON.getCyrilicName());
							dto.setCost(getCost(Rarity.UNCOMMON) + 750);
							break;
						case 46:
						case 47:
							dto.setName("Доспех +1 (чешуйчатый)");
							dto.setRarity(Rarity.RARE.getCyrilicName());
							dto.setCost(getCost(Rarity.UNCOMMON) + 50);
							break;
						case 48:
						case 49:
							dto.setName("Доспех +2 (кираса)");
							dto.setRarity(Rarity.RARE.getCyrilicName());
							dto.setCost(getCost(Rarity.RARE) + 400);
							break;
						case 50:
						case 51:
							dto.setName("Доспех +2 (наборной)");
							dto.setRarity(Rarity.RARE.getCyrilicName());
							dto.setCost(getCost(Rarity.RARE) + 200);
							break;
						case 52:
						case 53:
							dto.setName("Доспех +2 (проклёпанная кожа)");
							dto.setRarity(Rarity.RARE.getCyrilicName());
							dto.setCost(getCost(Rarity.RARE) + 45);
							break;
						case 54:
						case 55:
							dto.setName("Доспех +3 (кожаный)");
							dto.setRarity(Rarity.VERY_RARE.getCyrilicName());
							dto.setCost(getCost(Rarity.VERY_RARE) + 10);
							break;
						case 56:
						case 57:
							dto.setName("Доспех +3 (кольчуга)");
							dto.setRarity(Rarity.VERY_RARE.getCyrilicName());
							dto.setCost(getCost(Rarity.VERY_RARE) + 75);
							break;
						case 58:
						case 59:
							dto.setName("Доспех +3 (кольчужная рубаха)");
							dto.setRarity(Rarity.VERY_RARE.getCyrilicName());
							dto.setCost(getCost(Rarity.VERY_RARE) + 50);
							break;
						case 76:
							switch (1 + rnd.nextInt(12)) {
							case 1:
							case 2:
								dto.setName("Доспех +2 (полулаты)");
								dto.setRarity(Rarity.RARE.getCyrilicName());
								dto.setCost(getCost(Rarity.RARE) + 750);
								break;
							case 3:
							case 4:
								dto.setName("Доспех +2 (латы)");
								dto.setRarity(Rarity.RARE.getCyrilicName());
								dto.setCost(getCost(Rarity.RARE) + 1500);
								break;
							case 5:
							case 6:
								dto.setName("Доспех +3 (проклёпанная кожа)");
								dto.setRarity(Rarity.VERY_RARE.getCyrilicName());
								dto.setCost(getCost(Rarity.VERY_RARE) + 750);
								break;
							case 7:
							case 8:
								dto.setName("Доспех +3 (кираса)");
								dto.setRarity(Rarity.VERY_RARE.getCyrilicName());
								dto.setCost(getCost(Rarity.VERY_RARE) + 750);
								break;
							case 9:
							case 10:
								dto.setName("Доспех +3 (набороной)");
								dto.setRarity(Rarity.VERY_RARE.getCyrilicName());
								dto.setCost(getCost(Rarity.VERY_RARE) + 750);
								break;
							case 11:
								dto.setName("Доспех +3 (полулаты)");
								dto.setRarity(Rarity.VERY_RARE.getCyrilicName());
								dto.setCost(getCost(Rarity.VERY_RARE) + 750);
								break;
							case 12:
								dto.setName("Доспех +3 (латы)");
								dto.setRarity(Rarity.VERY_RARE.getCyrilicName());
								dto.setCost(getCost(Rarity.VERY_RARE) + 1550);
								break;
							}
							break;
						}
					}
					if (dto.getName().contains("Боеприпасы")) {
						int rb = rnd.nextInt(12);
						if (rb <= 6) {
							dto.setName(dto.getName() + " (стрелы)");
						} else if (rb < 9) {
							dto.setName(dto.getName() + " (болты)");
						} else if (rb < 10) {
							dto.setName(dto.getName() + " (дротики)");
						} else if (rb < 11) {
							dto.setName(dto.getName() + " (снаряды для пращи)");
						}
					} else if (dto.getName().contains("Свиток Заклинания")) {
						if (dto.getName().contains("заговор")) {
							List<Spell> spells = spellRepo.findByLevelAndBook_type((byte) 0, TypeBook.OFFICAL);
							Spell spell = spells.get(rnd.nextInt(spells.size()));
							dto.setName("Свиток Заклинания " + "(Заговор: " + spell.getName().toLowerCase() + ")");
						}
						if (dto.getName().contains("1")) {
							List<Spell> spells = spellRepo.findByLevelAndBook_type((byte) 1, TypeBook.OFFICAL);
							Spell spell = spells.get(rnd.nextInt(spells.size()));
							dto.setName("Свиток Заклинания " + "(1-ый уровень: " + spell.getName().toLowerCase() + ")");
						}
						if (dto.getName().contains("2")) {
							List<Spell> spells = spellRepo.findByLevelAndBook_type((byte) 2, TypeBook.OFFICAL);
							Spell spell = spells.get(rnd.nextInt(spells.size()));
							dto.setName("Свиток Заклинания " + "(2-ой уровень: " + spell.getName().toLowerCase() + ")");
						}
						if (dto.getName().contains("3")) {
							List<Spell> spells = spellRepo.findByLevelAndBook_type((byte) 3, TypeBook.OFFICAL);
							Spell spell = spells.get(rnd.nextInt(spells.size()));
							dto.setName("Свиток Заклинания " + "(3-ий уровень: " + spell.getName().toLowerCase() + ")");
						}
						if (dto.getName().contains("4")) {
							List<Spell> spells = spellRepo.findByLevelAndBook_type((byte) 4, TypeBook.OFFICAL);
							Spell spell = spells.get(rnd.nextInt(spells.size()));
							dto.setName("Свиток Заклинания " + "(4-ый уровень: " + spell.getName().toLowerCase() + ")");
						}
						if (dto.getName().contains("5")) {
							List<Spell> spells = spellRepo.findByLevelAndBook_type((byte) 5, TypeBook.OFFICAL);
							Spell spell = spells.get(rnd.nextInt(spells.size()));
							dto.setName("Свиток Заклинания " + "(5-ый уровень: " + spell.getName().toLowerCase() + ")");
						}
						if (dto.getName().contains("6")) {
							List<Spell> spells = spellRepo.findByLevelAndBook_type((byte) 6, TypeBook.OFFICAL);
							Spell spell = spells.get(rnd.nextInt(spells.size()));
							dto.setName("Свиток Заклинания " + "(6-ой уровень: " + spell.getName().toLowerCase() + ")");
						}
						if (dto.getName().contains("7")) {
							List<Spell> spells = spellRepo.findByLevelAndBook_type((byte) 7, TypeBook.OFFICAL);
							Spell spell = spells.get(rnd.nextInt(spells.size()));
							dto.setName("Свиток Заклинания " + "(7-ой уровень: " + spell.getName().toLowerCase() + ")");
						}
						if (dto.getName().contains("8")) {
							List<Spell> spells = spellRepo.findByLevelAndBook_type((byte) 8, TypeBook.OFFICAL);
							Spell spell = spells.get(rnd.nextInt(spells.size()));
							dto.setName("Свиток Заклинания " + "(8-ой уровень: " + spell.getName().toLowerCase() + ")");
						}
						if (dto.getName().contains("9")) {
							List<Spell> spells = spellRepo.findByLevelAndBook_type((byte) 9, TypeBook.OFFICAL);
							Spell spell = spells.get(rnd.nextInt(spells.size()));
							dto.setName("Свиток Заклинания " + "(9-ый уровень: " + spell.getName().toLowerCase() + ")");
						}
					} else if (dto.getName().contains("Оружие")) {
						List<Weapon> weapons = weaponRepo.findAll();
						Weapon weapon = weapons.get(rnd.nextInt(weapons.size()));
						dto.setName(dto.getName() + " (" + weapon.getName().toLowerCase() + ")");
					} else if (tableName.equals("Е1") && ri >= 12 && ri <= 14) {
						switch (1 + rnd.nextInt(8)) {
						case 1:
							dto.setName("Статуэтка чудесной силы " + "(Бронзовый грифон)");
							break;
						case 2:
							dto.setName("Статуэтка чудесной силы " + "(Эбеновая муха)");
							break;
						case 3:
							dto.setName("Статуэтка чудесной силы " + "(Золотые львы)");
							break;
						case 4:
							dto.setName("Статуэтка чудесной силы " + "(Костяные козлы)");
							break;
						case 5:
							dto.setName("Статуэтка чудесной силы " + "(Мраморный слон)");
							break;
						case 6:
						case 7:
							dto.setName("Статуэтка чудесной силы " + "(Ониксовая собака)");
							break;
						case 8:
							dto.setName("Статуэтка чудесной силы " + "(Серпентиновая сова)");
							break;
						}
					} else if (dto.getName().contains("Зелье Сопротивления")) {
						dto.setName("Зелье Сопротивления" + " " + getResistenceType());
					} else if (dto.getName().contains("Доспех Сопротивления")) {
						dto.setName(dto.getName() + " " + getResistenceType());
					}
					list.add(dto);
				}
			}
		}
		return list;
	}

	private String getResistenceType() {
		switch (1 + rnd.nextInt(10)) {
		case 1:
			return "(звуку)";
		case 2:
			return "(излучению)";
		case 3:
			return "(кислоте)";
		case 4:
			return "(некротической энергии)";
		case 5:
			return "(огню)";
		case 6:
			return "(психической энергии)";
		case 7:
			return "(силовому полю)";
		case 8:
			return "(холоду)";
		case 9:
			return "(электричеству)";
		case 10:
			return "(яду)";
		}
		return "";
	}

	private int getCost(Rarity rarity) {
		switch (rarity) {
		case COMMON:
			return (2 + rnd.nextInt(6)) * 10;
		case UNCOMMON:
			return (2 + rnd.nextInt(6)) * 100;
		case RARE:
			return (2 + rnd.nextInt(10) + rnd.nextInt(10)) * 1000;
		case VERY_RARE:
			return (2 + rnd.nextInt(4)) * 10000;
		case LEGENDARY:
			return (2 + rnd.nextInt(6) + rnd.nextInt(6)) * 25000;
		default:
			return 0;
		}
	}

	@GetMapping("/treasure")
	public String getTreasureForm(Model model) {
		model.addAttribute("result", 1);
		model.addAttribute("treasures", Collections.emptyList());
		model.addAttribute("things", Collections.emptyList());
		return "calc/treasure";
	}

	@PostMapping("/treasure")
	public String getTreasures(Model model, Integer cr) {
		int coper = 0;
		int silver = 0;
		int gold = 0;
		int electrum = 0;
		int platina = 0;
		if (cr == 1) {
			for (int i = 0; i < 6; i++) {
				coper += 1 + rnd.nextInt(6);
			}
			coper *= 100;
			for (int i = 0; i < 3; i++) {
				silver += 1 + rnd.nextInt(6);
			}
			silver *= 100;
			for (int i = 0; i < 2; i++) {
				gold += 1 + rnd.nextInt(6);
			}
			gold *= 10;
		} else if (cr == 2) {
			for (int i = 0; i < 2; i++) {
				coper += 1 + rnd.nextInt(6);
			}
			coper *= 100;
			for (int i = 0; i < 2; i++) {
				silver += 1 + rnd.nextInt(6);
			}
			silver *= 1000;
			for (int i = 0; i < 6; i++) {
				gold += 1 + rnd.nextInt(6);
			}
			gold *= 100;
			for (int i = 0; i < 3; i++) {
				platina += 1 + rnd.nextInt(6);
			}
			platina *= 10;
		} else if (cr == 3) {
			for (int i = 0; i < 4; i++) {
				gold += 1 + rnd.nextInt(6);
			}
			gold *= 14000;
			for (int i = 0; i < 5; i++) {
				platina += 1 + rnd.nextInt(6);
			}
			platina *= 100;
		} else if (cr == 4) {
			for (int i = 0; i < 12; i++) {
				gold += 1 + rnd.nextInt(6);
			}
			gold *= 42_000;
			for (int i = 0; i < 8; i++) {
				platina += 1 + rnd.nextInt(6);
			}
			platina *= 1_000;
		}

		model.addAttribute("coper", coper);
		model.addAttribute("silver", silver);
		model.addAttribute("electrum", electrum);
		model.addAttribute("gold", gold);
		model.addAttribute("platina", platina);

		List<MagicThingDto> things = new ArrayList<>();
		int ri = 1 + rnd.nextInt(100);

		if (cr == 1) {
			if (ri >= 37 && ri <= 60) {
				things.addAll(getMagicThings(ri, 1, 1000, "А", 6));
			} else if (ri >= 61 && ri <= 75) {
				things.addAll(getMagicThings(ri, 1, 1000, "Б", 4));
			} else if (ri >= 76 && ri <= 85) {
				things.addAll(getMagicThings(ri, 1, 1000, "В", 4));
			} else if (ri >= 86 && ri <= 97) {
				things.addAll(getMagicThings(ri, 1, 1000, "Е", 4));
			} else if (ri >= 98 && ri <= 100) {
				things.addAll(getMagicThings(ri, 1, 1000, "Е1", 1));
			}
		} else if (cr == 2) {
			if (ri >= 29 && ri <= 44) {
				things.addAll(getMagicThings(ri, 1, 1000, "А", 6));
			} else if (ri >= 45 && ri <= 63) {
				things.addAll(getMagicThings(ri, 1, 1000, "Б", 4));
			} else if (ri >= 64 && ri <= 74) {
				things.addAll(getMagicThings(ri, 1, 1000, "В", 4));
			} else if (ri >= 75 && ri <= 80) {
				things.addAll(getMagicThings(ri, 1, 1000, "Г", 1));
			} else if (ri >= 81 && ri <= 94) {
				things.addAll(getMagicThings(ri, 1, 1000, "Е", 4));
			} else if (ri >= 95 && ri <= 98) {
				things.addAll(getMagicThings(ri, 1, 1000, "Е1", 4));
			} else if (ri >= 96 && ri <= 100) {
				things.addAll(getMagicThings(ri, 1, 1000, "Ж", 1));
			}
		} else if (cr == 3) {
			if (ri >= 16 && ri <= 29) {
				things.addAll(getMagicThings(ri, 1, 1000, "А", 4));
				things.addAll(getMagicThings(ri, 1, 1000, "Б", 6));
			} else if (ri >= 30 && ri <= 50) {
				things.addAll(getMagicThings(ri, 1, 1000, "В", 6));
			} else if (ri >= 51 && ri <= 66) {
				things.addAll(getMagicThings(ri, 1, 1000, "Г", 4));
			} else if (ri >= 67 && ri <= 74) {
				things.addAll(getMagicThings(ri, 1, 1000, "Д", 1));
			} else if (ri >= 75 && ri <= 82) {
				things.addAll(getMagicThings(ri, 1, 1000, "Е", 1));
				things.addAll(getMagicThings(ri, 1, 1000, "Е1", 4));
			} else if (ri >= 83 && ri <= 92) {
				things.addAll(getMagicThings(ri, 1, 1000, "Ж", 4));
			} else if (ri >= 93 && ri <= 100) {
				things.addAll(getMagicThings(ri, 1, 1000, "З", 1));
			}
		} else if (cr == 4) {
			if (ri >= 3 && ri <= 14) {
				things.addAll(getMagicThings(ri, 1, 1000, "В", 8));
			} else if (ri >= 15 && ri <= 46) {
				things.addAll(getMagicThings(ri, 1, 1000, "Г", 6));
			} else if (ri >= 47 && ri <= 68) {
				things.addAll(getMagicThings(ri, 1, 1000, "Д", 6));
			} else if (ri >= 69 && ri <= 72) {
				things.addAll(getMagicThings(ri, 1, 1000, "Е1", 6));
			} else if (ri >= 73 && ri <= 80) {
				things.addAll(getMagicThings(ri, 1, 1000, "Ж", 4));
			} else if (ri >= 81 && ri <= 100) {
				things.addAll(getMagicThings(ri, 1, 1000, "З", 4));
			}
		}
		Collections.sort(things, Comparator.comparing(MagicThingDto::getCost));
		model.addAttribute("things", things);

		List<Treasure> treasures = new ArrayList<>();
		if (cr == 1) {
			if (ri >= 7 && ri <= 16) {
				treasures.addAll(getTreasures(10, TreasureType.GEM, 2, 6));
			} else if (ri >= 17 && ri <= 26) {
				treasures.addAll(getTreasures(25, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 27 && ri <= 36) {
				treasures.addAll(getTreasures(50, TreasureType.GEM, 2, 6));
			} else if (ri >= 37 && ri <= 44) {
				treasures.addAll(getTreasures(10, TreasureType.GEM, 2, 6));
			} else if (ri >= 45 && ri <= 52) {
				treasures.addAll(getTreasures(25, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 53 && ri <= 60) {
				treasures.addAll(getTreasures(50, TreasureType.GEM, 2, 6));
			} else if (ri >= 61 && ri <= 65) {
				treasures.addAll(getTreasures(10, TreasureType.GEM, 2, 6));
			} else if (ri >= 66 && ri <= 70) {
				treasures.addAll(getTreasures(25, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 71 && ri <= 75) {
				treasures.addAll(getTreasures(50, TreasureType.GEM, 2, 6));
			} else if (ri >= 76 && ri <= 78) {
				treasures.addAll(getTreasures(10, TreasureType.GEM, 2, 6));
			} else if (ri >= 79 && ri <= 80) {
				treasures.addAll(getTreasures(25, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 81 && ri <= 85) {
				treasures.addAll(getTreasures(50, TreasureType.GEM, 2, 6));
			} else if (ri >= 86 && ri <= 92) {
				treasures.addAll(getTreasures(25, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 93 && ri <= 97) {
				treasures.addAll(getTreasures(50, TreasureType.GEM, 2, 6));
			} else if (ri >= 98 && ri <= 99) {
				treasures.addAll(getTreasures(25, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri == 100) {
				treasures.addAll(getTreasures(50, TreasureType.GEM, 2, 6));
			}
		} else if (cr == 2) {
			if (ri >= 5 && ri <= 10) {
				treasures.addAll(getTreasures(25, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 11 && ri <= 16) {
				treasures.addAll(getTreasures(50, TreasureType.GEM, 3, 6));
			} else if (ri >= 17 && ri <= 22) {
				treasures.addAll(getTreasures(100, TreasureType.GEM, 3, 6));
			} else if (ri >= 23 && ri <= 28) {
				treasures.addAll(getTreasures(250, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 29 && ri <= 32) {
				treasures.addAll(getTreasures(25, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 33 && ri <= 36) {
				treasures.addAll(getTreasures(50, TreasureType.GEM, 3, 6));
			} else if (ri >= 37 && ri <= 40) {
				treasures.addAll(getTreasures(100, TreasureType.GEM, 3, 6));
			} else if (ri >= 41 && ri <= 44) {
				treasures.addAll(getTreasures(250, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 45 && ri <= 49) {
				treasures.addAll(getTreasures(25, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 50 && ri <= 54) {
				treasures.addAll(getTreasures(100, TreasureType.GEM, 3, 6));
			} else if (ri >= 55 && ri <= 59) {
				treasures.addAll(getTreasures(100, TreasureType.GEM, 3, 6));
			} else if (ri >= 60 && ri <= 63) {
				treasures.addAll(getTreasures(250, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 64 && ri <= 66) {
				treasures.addAll(getTreasures(25, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 67 && ri <= 69) {
				treasures.addAll(getTreasures(50, TreasureType.GEM, 3, 6));
			} else if (ri >= 70 && ri <= 72) {
				treasures.addAll(getTreasures(100, TreasureType.GEM, 3, 6));
			} else if (ri >= 73 && ri <= 74) {
				treasures.addAll(getTreasures(250, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 75 && ri <= 76) {
				treasures.addAll(getTreasures(25, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 77 && ri <= 78) {
				treasures.addAll(getTreasures(50, TreasureType.GEM, 3, 6));
			} else if (ri == 79) {
				treasures.addAll(getTreasures(100, TreasureType.GEM, 3, 6));
			} else if (ri == 80) {
				treasures.addAll(getTreasures(250, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 81 && ri <= 84) {
				treasures.addAll(getTreasures(25, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 85 && ri <= 88) {
				treasures.addAll(getTreasures(50, TreasureType.GEM, 3, 6));
			} else if (ri >= 89 && ri <= 91) {
				treasures.addAll(getTreasures(100, TreasureType.GEM, 3, 6));
			} else if (ri >= 92 && ri <= 94) {
				treasures.addAll(getTreasures(250, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 95 && ri <= 96) {
				treasures.addAll(getTreasures(100, TreasureType.GEM, 3, 6));
			} else if (ri >= 97 && ri <= 98) {
				treasures.addAll(getTreasures(250, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri == 99) {
				treasures.addAll(getTreasures(100, TreasureType.GEM, 3, 6));
			} else if (ri == 100) {
				treasures.addAll(getTreasures(250, TreasureType.WORKS_OF_ART, 2, 4));
			}
		} else if (cr == 3) {
			if (ri >= 4 && ri <= 6) {
				treasures.addAll(getTreasures(250, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 7 && ri <= 9) {
				treasures.addAll(getTreasures(750, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 10 && ri <= 12) {
				treasures.addAll(getTreasures(500, TreasureType.GEM, 3, 6));
			} else if (ri >= 13 && ri <= 15) {
				treasures.addAll(getTreasures(1000, TreasureType.GEM, 3, 6));
			} else if (ri >= 16 && ri <= 19) {
				treasures.addAll(getTreasures(250, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 20 && ri <= 23) {
				treasures.addAll(getTreasures(750, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 24 && ri <= 26) {
				treasures.addAll(getTreasures(500, TreasureType.GEM, 3, 6));
			} else if (ri >= 27 && ri <= 29) {
				treasures.addAll(getTreasures(1000, TreasureType.GEM, 3, 6));
			} else if (ri >= 30 && ri <= 35) {
				treasures.addAll(getTreasures(250, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 36 && ri <= 40) {
				treasures.addAll(getTreasures(750, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 41 && ri <= 45) {
				treasures.addAll(getTreasures(500, TreasureType.GEM, 3, 6));
			} else if (ri >= 46 && ri <= 50) {
				treasures.addAll(getTreasures(1000, TreasureType.GEM, 3, 6));
			} else if (ri >= 51 && ri <= 54) {
				treasures.addAll(getTreasures(250, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 55 && ri <= 58) {
				treasures.addAll(getTreasures(750, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 59 && ri <= 62) {
				treasures.addAll(getTreasures(500, TreasureType.GEM, 3, 6));
			} else if (ri >= 63 && ri <= 66) {
				treasures.addAll(getTreasures(1000, TreasureType.GEM, 3, 6));
			} else if (ri >= 67 && ri <= 68) {
				treasures.addAll(getTreasures(250, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 69 && ri <= 70) {
				treasures.addAll(getTreasures(750, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 71 && ri <= 72) {
				treasures.addAll(getTreasures(500, TreasureType.GEM, 3, 6));
			} else if (ri >= 73 && ri <= 74) {
				treasures.addAll(getTreasures(1000, TreasureType.GEM, 3, 6));
			} else if (ri >= 75 && ri <= 76) {
				treasures.addAll(getTreasures(250, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 77 && ri <= 78) {
				treasures.addAll(getTreasures(750, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 79 && ri <= 80) {
				treasures.addAll(getTreasures(500, TreasureType.GEM, 3, 6));
			} else if (ri >= 81 && ri <= 82) {
				treasures.addAll(getTreasures(1000, TreasureType.GEM, 3, 6));
			} else if (ri >= 83 && ri <= 85) {
				treasures.addAll(getTreasures(250, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 86 && ri <= 88) {
				treasures.addAll(getTreasures(750, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 89 && ri <= 90) {
				treasures.addAll(getTreasures(500, TreasureType.GEM, 3, 6));
			} else if (ri >= 91 && ri <= 92) {
				treasures.addAll(getTreasures(1000, TreasureType.GEM, 3, 6));
			} else if (ri >= 93 && ri <= 94) {
				treasures.addAll(getTreasures(250, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 95 && ri <= 96) {
				treasures.addAll(getTreasures(750, TreasureType.WORKS_OF_ART, 2, 4));
			} else if (ri >= 97 && ri <= 98) {
				treasures.addAll(getTreasures(500, TreasureType.GEM, 3, 6));
			} else if (ri >= 99 && ri <= 100) {
				treasures.addAll(getTreasures(1000, TreasureType.GEM, 3, 6));
			}
		} else if (cr == 4) {
			if (ri >= 3 && ri <= 5) {
				treasures.addAll(getTreasures(1000, TreasureType.GEM, 3, 6));
			} else if (ri >= 6 && ri <= 8) {
				treasures.addAll(getTreasures(2500, TreasureType.WORKS_OF_ART, 1, 10));
			} else if (ri >= 9 && ri <= 11) {
				treasures.addAll(getTreasures(7500, TreasureType.WORKS_OF_ART, 1, 4));
			} else if (ri >= 12 && ri <= 14) {
				treasures.addAll(getTreasures(5000, TreasureType.GEM, 1, 8));
			} else if (ri >= 15 && ri <= 22) {
				treasures.addAll(getTreasures(1000, TreasureType.GEM, 3, 6));
			} else if (ri >= 23 && ri <= 30) {
				treasures.addAll(getTreasures(2500, TreasureType.WORKS_OF_ART, 1, 10));
			} else if (ri >= 31 && ri <= 38) {
				treasures.addAll(getTreasures(7500, TreasureType.WORKS_OF_ART, 1, 4));
			} else if (ri >= 39 && ri <= 46) {
				treasures.addAll(getTreasures(5000, TreasureType.GEM, 1, 8));
			} else if (ri >= 47 && ri <= 52) {
				treasures.addAll(getTreasures(1000, TreasureType.GEM, 3, 6));
			} else if (ri >= 53 && ri <= 58) {
				treasures.addAll(getTreasures(2500, TreasureType.WORKS_OF_ART, 1, 10));
			} else if (ri >= 59 && ri <= 63) {
				treasures.addAll(getTreasures(7500, TreasureType.WORKS_OF_ART, 1, 4));
			} else if (ri >= 64 && ri <= 68) {
				treasures.addAll(getTreasures(5000, TreasureType.GEM, 1, 8));
			} else if (ri == 69) {
				treasures.addAll(getTreasures(1000, TreasureType.GEM, 3, 6));
			} else if (ri == 70) {
				treasures.addAll(getTreasures(2500, TreasureType.WORKS_OF_ART, 1, 10));
			} else if (ri == 71) {
				treasures.addAll(getTreasures(7500, TreasureType.WORKS_OF_ART, 1, 4));
			} else if (ri == 72) {
				treasures.addAll(getTreasures(5000, TreasureType.GEM, 1, 8));
			} else if (ri >= 73 && ri <= 74) {
				treasures.addAll(getTreasures(1000, TreasureType.GEM, 3, 6));
			} else if (ri >= 75 && ri <= 76) {
				treasures.addAll(getTreasures(2500, TreasureType.WORKS_OF_ART, 1, 10));
			} else if (ri >= 77 && ri <= 78) {
				treasures.addAll(getTreasures(7500, TreasureType.WORKS_OF_ART, 1, 4));
			} else if (ri >= 79 && ri <= 80) {
				treasures.addAll(getTreasures(500, TreasureType.GEM, 1, 8));
			} else if (ri >= 81 && ri <= 85) {
				treasures.addAll(getTreasures(1000, TreasureType.GEM, 3, 6));
			} else if (ri >= 86 && ri <= 90) {
				treasures.addAll(getTreasures(2500, TreasureType.WORKS_OF_ART, 1, 10));
			} else if (ri >= 91 && ri <= 95) {
				treasures.addAll(getTreasures(7500, TreasureType.WORKS_OF_ART, 1, 4));
			} else if (ri >= 96 && ri <= 100) {
				treasures.addAll(getTreasures(5000, TreasureType.GEM, 1, 8));
			}
		}
		model.addAttribute("treasures", treasures);
		model.addAttribute("cr", cr);
		return "calc/treasure";
	}

	private List<Treasure> getTreasures(int cost, TreasureType type, int count, int dice) {
		List<Treasure> treasures = new ArrayList<>();
		int ri = getCountDice(count, dice);
		for (int i = 0; i < count; i++) {
			ri += 1 + rnd.nextInt(dice);
		}
		for (int i = 0; i < ri; i++) {
			List<Treasure> treasuresFind = treasureRepo.findAllByCostAndType(cost, type);
			treasures.add(treasuresFind.get(rnd.nextInt(treasuresFind.size())));
		}
		return treasures;
	}

	private int getCountDice(int count, int dice) {
		int ri = 0;
		for (int i = 0; i < count; i++) {
			ri += 1 + rnd.nextInt(dice);
		}
		return ri;
	}
}