package com.prism.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Non-functional requirement suggestion")
public class NonFunctionalRequirement {
    
    @JsonProperty("category")
    @Schema(description = "Category of the NFR (e.g., Performance, Security, Usability)")
    private String category;
    
    @JsonProperty("requirement")
    @Schema(description = "The suggested non-functional requirement")
    private String requirement;
    
    @JsonProperty("priority")
    @Schema(description = "Priority level of this NFR")
    private String priority;

    // Constructors
    public NonFunctionalRequirement() {}

    public NonFunctionalRequirement(String category, String requirement, String priority) {
        this.category = category;
        this.requirement = requirement;
        this.priority = priority;
    }

    // Getters and Setters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}