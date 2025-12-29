package com.prism.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Entities extracted from the requirement text")
public class ExtractedEntities {
    
    @JsonProperty("actors")
    @Schema(description = "List of actors/users mentioned in the requirement")
    private List<String> actors;
    
    @JsonProperty("actions")
    @Schema(description = "List of actions/verbs mentioned in the requirement")
    private List<String> actions;
    
    @JsonProperty("objects")
    @Schema(description = "List of objects/nouns mentioned in the requirement")
    private List<String> objects;

    // Constructors
    public ExtractedEntities() {}

    public ExtractedEntities(List<String> actors, List<String> actions, List<String> objects) {
        this.actors = actors;
        this.actions = actions;
        this.objects = objects;
    }

    // Getters and Setters
    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    public List<String> getObjects() {
        return objects;
    }

    public void setObjects(List<String> objects) {
        this.objects = objects;
    }
}