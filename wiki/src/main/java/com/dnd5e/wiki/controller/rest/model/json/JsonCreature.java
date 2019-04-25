
package com.dnd5e.wiki.controller.rest.model.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "AC",
    "Abilities",
    "Actions",
    "Challenge",
    "ConditionImmunities",
    "DamageImmunities",
    "DamageResistances",
    "DamageVulnerabilities",
    "Description",
    "Environment",
    "GroupTags",
    "HP",
    "InitiativeModifier",
    "Languages",
    "LegendaryActions",
    "Name",
    "Reactions",
    "Saves",
    "Senses",
    "Skills",
    "Source",
    "Speed",
    "Traits",
    "Type",
    "Unique",
    "id",
    "proficiency"
})
public class JsonCreature {

    @JsonProperty("AC")
    public AC aC;
    @JsonProperty("Abilities")
    public Abilities abilities;
    @JsonProperty("Actions")
    public List<Action> actions = null;
    @JsonProperty("Challenge")
    public String challenge;
    @JsonProperty("ConditionImmunities")
    public List<String> conditionImmunities = null;
    @JsonProperty("DamageImmunities")
    public List<String> damageImmunities = null;
    @JsonProperty("DamageResistances")
    public List<String> damageResistances = null;
    @JsonProperty("DamageVulnerabilities")
    public List<Object> damageVulnerabilities = null;
    @JsonProperty("Description")
    public String description;
    @JsonProperty("Environment")
    public List<String> environment = null;
    @JsonProperty("GroupTags")
    public String groupTags;
    @JsonProperty("HP")
    public HP hP;
    @JsonProperty("InitiativeModifier")
    public String initiativeModifier;
    @JsonProperty("Languages")
    public List<String> languages = null;
    @JsonProperty("LegendaryActions")
    public List<LegendaryAction> legendaryActions = null;
    @JsonProperty("Name")
    public String name;
    @JsonProperty("Reactions")
    public List<Reaction> reactions = null;
    @JsonProperty("Saves")
    public List<Safe> saves = null;
    @JsonProperty("Senses")
    public List<String> senses = null;
    @JsonProperty("Skills")
    public List<Skill> skills = null;
    @JsonProperty("Source")
    public String source;
    @JsonProperty("Speed")
    public List<String> speed = null;
    @JsonProperty("Traits")
    public List<TraitJS> traits = null;
    @JsonProperty("Type")
    public String type;
    @JsonProperty("Unique")
    public Boolean unique;
    @JsonProperty("id")
    public String id;
    @JsonProperty("proficiency")
    public String proficiency;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public JsonCreature withAC(AC aC) {
        this.aC = aC;
        return this;
    }

    public JsonCreature withAbilities(Abilities abilities) {
        this.abilities = abilities;
        return this;
    }

    public JsonCreature withActions(List<Action> actions) {
        this.actions = actions;
        return this;
    }

    public JsonCreature withChallenge(String challenge) {
        this.challenge = challenge;
        return this;
    }

    public JsonCreature withConditionImmunities(List<String> conditionImmunities) {
        this.conditionImmunities = conditionImmunities;
        return this;
    }

    public JsonCreature withDamageImmunities(List<String> damageImmunities) {
        this.damageImmunities = damageImmunities;
        return this;
    }

    public JsonCreature withDamageResistances(List<String> damageResistances) {
        this.damageResistances = damageResistances;
        return this;
    }

    public JsonCreature withDamageVulnerabilities(List<Object> damageVulnerabilities) {
        this.damageVulnerabilities = damageVulnerabilities;
        return this;
    }

    public JsonCreature withDescription(String description) {
        this.description = description;
        return this;
    }

    public JsonCreature withEnvironment(List<String> environment) {
        this.environment = environment;
        return this;
    }

    public JsonCreature withGroupTags(String groupTags) {
        this.groupTags = groupTags;
        return this;
    }

    public JsonCreature withHP(HP hP) {
        this.hP = hP;
        return this;
    }

    public JsonCreature withInitiativeModifier(String initiativeModifier) {
        this.initiativeModifier = initiativeModifier;
        return this;
    }

    public JsonCreature withLanguages(List<String> languages) {
        this.languages = languages;
        return this;
    }

    public JsonCreature withLegendaryActions(List<LegendaryAction> legendaryActions) {
        this.legendaryActions = legendaryActions;
        return this;
    }

    public JsonCreature withName(String name) {
        this.name = name;
        return this;
    }

    public JsonCreature withReactions(List<Reaction> reactions) {
        this.reactions = reactions;
        return this;
    }

    public JsonCreature withSaves(List<Safe> saves) {
        this.saves = saves;
        return this;
    }

    public JsonCreature withSenses(List<String> senses) {
        this.senses = senses;
        return this;
    }

    public JsonCreature withSkills(List<Skill> skills) {
        this.skills = skills;
        return this;
    }

    public JsonCreature withSource(String source) {
        this.source = source;
        return this;
    }

    public JsonCreature withSpeed(List<String> speed) {
        this.speed = speed;
        return this;
    }

    public JsonCreature withTraits(List<TraitJS> traits) {
        this.traits = traits;
        return this;
    }

    public JsonCreature withType(String type) {
        this.type = type;
        return this;
    }

    public JsonCreature withUnique(Boolean unique) {
        this.unique = unique;
        return this;
    }

    public JsonCreature withId(String id) {
        this.id = id;
        return this;
    }

    public JsonCreature withProficiency(String proficiency) {
        this.proficiency = proficiency;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public JsonCreature withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
