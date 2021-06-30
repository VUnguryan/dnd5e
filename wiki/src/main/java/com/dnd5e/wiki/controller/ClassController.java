package com.dnd5e.wiki.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnd5e.wiki.controller.rest.SettingRestController;
import com.dnd5e.wiki.dto.ClassFetureDto;
import com.dnd5e.wiki.dto.user.Setting;
import com.dnd5e.wiki.model.TypeBook;
import com.dnd5e.wiki.model.hero.classes.Archetype;
import com.dnd5e.wiki.model.hero.classes.HeroClass;
import com.dnd5e.wiki.repository.ClassRepository;
import com.dnd5e.wiki.util.SourceUtil;

@Controller
@RequestMapping("/hero/classes")
public class ClassController {
	@Autowired
	private HttpSession session;

	private ClassRepository classRepo;

	@Autowired
	public void setClassRepository(ClassRepository repository) {
		this.classRepo = repository;
	}

	@GetMapping
	public String getClasses(Model model, Device device) {
		model.addAttribute("device", device);
		if (device.isMobile()) {
			Setting settings = (Setting) session.getAttribute(SettingRestController.SETTINGS);
			model.addAttribute("classes", classRepo.findAllBySources(SourceUtil.getSources(settings)));
			return "classes";
		}
		return "classes2";
	}

	@GetMapping("/old")
	public String getClasses(Model model) {
		Setting settings = (Setting) session.getAttribute(SettingRestController.SETTINGS);
		model.addAttribute("classes", classRepo.findAllBySources(SourceUtil.getSources(settings)));
		return "classes";
	}

	@GetMapping("/fragment/class/{id}")
	public String getFragmentClasses(Model model, Device device, @PathVariable Integer id) {
		model.addAttribute("device", device);
		HeroClass heroClass = classRepo.findById(id).get();
		Setting settings = (Setting) session.getAttribute(SettingRestController.SETTINGS);
		Set<TypeBook> sources = SourceUtil.getSources(settings);
		List<Archetype> archetypes = heroClass.getArchetypes().stream()
				.filter(a -> sources.contains(a.getBook().getType()))
				.collect(Collectors.toList());
		Collections.sort(archetypes, Comparator.comparing(Archetype::getBook));
		heroClass.setArchetypes(archetypes);
		List<ClassFetureDto> features = new ArrayList<>();
		heroClass.getTraits().stream()
			.filter(f -> !f.isArchitype())
			.map(f -> new ClassFetureDto(f, heroClass.getName()))
			.forEach(f -> features.add(f));
		Map<Integer, Set<ClassFetureDto>> archetypeTraits = heroClass.getArchetypes()
				.stream().flatMap(a -> a.getFeats().stream())
				.collect(
						Collectors.groupingBy(
								f -> f.getArchetype().getId(),
								Collectors.mapping(f -> new ClassFetureDto(
										f, f.getArchetype().getGenitiveName()), 
										Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(ClassFetureDto::getLevel).thenComparing(ClassFetureDto::getName))))
								)
				);
		Collections.sort(features, Comparator.comparing(ClassFetureDto::getLevel));
		model.addAttribute("features", features);
		model.addAttribute("heroClass", heroClass);
		model.addAttribute("archetypeTraits", archetypeTraits);
		model.addAttribute("order", "[[ 1, 'asc' ]]");
		return "classView :: heroClass";
	}

	@GetMapping("/class/{id}")
	public String getClass(Model model, Device device, @PathVariable Integer id) {
		model.addAttribute("device", device);
		HeroClass heroClass = classRepo.findById(id).get();
		Setting settings = (Setting) session.getAttribute(SettingRestController.SETTINGS);
		Set<TypeBook> sources = SourceUtil.getSources(settings);
		List<Archetype> archetypes = heroClass.getArchetypes().stream()
				.filter(a -> sources.contains(a.getBook().getType()))
				.collect(Collectors.toList());
		Collections.sort(archetypes, Comparator.comparing(Archetype::getBook));
		heroClass.setArchetypes(archetypes);
		List<ClassFetureDto> features = new ArrayList<>();
		heroClass.getTraits().stream()
			.filter(f -> !f.isArchitype())
			.map(f -> new ClassFetureDto(f, heroClass.getName()))
			.forEach(f -> features.add(f));
		Map<Integer, Set<ClassFetureDto>> archetypeTraits = heroClass.getArchetypes()
				.stream().flatMap(a -> a.getFeats().stream())
				.collect(
						Collectors.groupingBy(
								f -> f.getArchetype().getId(),
								Collectors.mapping(f -> new ClassFetureDto(
										f, f.getArchetype().getGenitiveName()), 
										Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(ClassFetureDto::getLevel).thenComparing(ClassFetureDto::getName))))
								)
				);
		Collections.sort(features, Comparator.comparing(ClassFetureDto::getLevel));
		model.addAttribute("features", features);
		model.addAttribute("heroClass", heroClass);
		model.addAttribute("archetypeTraits", archetypeTraits);
		model.addAttribute("order", "[[ 1, 'asc' ]]");
		return "classView";
	}
	
	@GetMapping("/class/{id}/archetype/{archetypeId}")
	public String getClassAndArchetype(Model model, Device device, @PathVariable Integer id, @PathVariable Integer archetypeId) {
		model.addAttribute("device", device);
		HeroClass heroClass = classRepo.findById(id).get();
		Setting settings = (Setting) session.getAttribute(SettingRestController.SETTINGS);
		Set<TypeBook> sources = SourceUtil.getSources(settings);
		List<Archetype> archetypes = heroClass.getArchetypes();
		archetypes = heroClass.getArchetypes().stream()
				.filter(a -> sources.contains(a.getBook().getType()) || a.getId()==archetypeId)
				.collect(Collectors.toList());
		Collections.sort(archetypes, Comparator.comparing(Archetype::getBook));
		//heroClass.setArchetypes(archetypes); //FIXME
		model.addAttribute("archetypes", archetypes);
		heroClass.setArchetypes(heroClass.getArchetypes().stream()
				.filter(a -> a.getId().equals(archetypeId))
				.collect(Collectors.toList()));

		List<ClassFetureDto> features = new ArrayList<>();
		heroClass.getTraits().stream()
			.filter(f -> !f.isArchitype())
			.map(f -> new ClassFetureDto(f, heroClass.getName()))
			.forEach(f -> features.add(f));
		Archetype archetype = heroClass.getArchetypes()
				.stream()
				.filter(a -> a.getId().equals(archetypeId))
				.findFirst().orElseGet(Archetype::new);

		ClassFetureDto feature = new ClassFetureDto();
		feature.setId(archetypeId);
		feature.setLevel(archetype.getLevel());
		feature.setDescription(archetype.getDescription());
		feature.setName(archetype.getName());
		feature.setPrefix("ad");
		if (archetype.getBook() != null) {
			feature.setType("Источник: " + archetype.getBook().getName()
					+ (archetype.getPage() == null ? "" : ", стр. " + archetype.getPage()));
		}
		features.add(feature);
		archetype.getFeats().stream()
			.map(f -> new ClassFetureDto(f, archetype.getGenitiveName()))
			.forEach(f -> features.add(f));

		Collections.sort(features, Comparator.comparing(ClassFetureDto::getLevel).thenComparing(ClassFetureDto::getOrder));
		model.addAttribute("archetypeName", archetype.getName());
		
		model.addAttribute("heroClass", heroClass);
		model.addAttribute("features", features);
		model.addAttribute("selectedArchetypeId", archetypeId);
		model.addAttribute("selectedArchetype", archetype);
		model.addAttribute("archetypeSpells", archetype.getSpells().stream().filter(s-> s.getLevel() != 0).collect(Collectors.toList()));
		model.addAttribute("order", "[[ 1, 'asc' ]]");
		return "archetypeView";
	}
}