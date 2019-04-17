
package com.dnd5e.wiki.controller.rest.model.json;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Cha",
    "Con",
    "Dex",
    "Int",
    "Str",
    "Wis"
})
public class Abilities {

    @JsonProperty("Cha")
    public String cha;
    @JsonProperty("Con")
    public String con;
    @JsonProperty("Dex")
    public String dex;
    @JsonProperty("Int")
    public String _int;
    @JsonProperty("Str")
    public String str;
    @JsonProperty("Wis")
    public String wis;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Abilities withCha(String cha) {
        this.cha = cha;
        return this;
    }

    public Abilities withCon(String con) {
        this.con = con;
        return this;
    }

    public Abilities withDex(String dex) {
        this.dex = dex;
        return this;
    }

    public Abilities withInt(String _int) {
        this._int = _int;
        return this;
    }

    public Abilities withStr(String str) {
        this.str = str;
        return this;
    }

    public Abilities withWis(String wis) {
        this.wis = wis;
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

    public Abilities withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}