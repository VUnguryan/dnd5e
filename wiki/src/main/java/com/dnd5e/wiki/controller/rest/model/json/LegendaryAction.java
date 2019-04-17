
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
    "Content",
    "Name",
    "Usage"
})
public class LegendaryAction {

    @JsonProperty("Content")
    public String content;
    @JsonProperty("Name")
    public String name;
    @JsonProperty("Usage")
    public String usage;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public LegendaryAction withContent(String content) {
        this.content = content;
        return this;
    }

    public LegendaryAction withName(String name) {
        this.name = name;
        return this;
    }

    public LegendaryAction withUsage(String usage) {
        this.usage = usage;
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

    public LegendaryAction withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
