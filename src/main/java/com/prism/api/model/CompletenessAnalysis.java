package com.prism.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Analysis of requirement completeness")
public class CompletenessAnalysis {
    
    @JsonProperty("completeness_score")
    @Schema(description = "Overall completeness score (0-100)")
    private double completenessScore;
    
    @JsonProperty("missing_elements")
    @Schema(description = "List of missing requirement elements")
    private List<String> missingElements;
    
    @JsonProperty("suggestions")
    @Schema(description = "Suggestions to improve completeness")
    private List<String> suggestions;

    // Constructors
    public CompletenessAnalysis() {}

    public CompletenessAnalysis(double completenessScore, List<String> missingElements, List<String> suggestions) {
        this.completenessScore = completenessScore;
        this.missingElements = missingElements;
        this.suggestions = suggestions;
    }

    // Getters and Setters
    public double getCompletenessScore() {
        return completenessScore;
    }

    public void setCompletenessScore(double completenessScore) {
        this.completenessScore = completenessScore;
    }

    public List<String> getMissingElements() {
        return missingElements;
    }

    public void setMissingElements(List<String> missingElements) {
        this.missingElements = missingElements;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }
}