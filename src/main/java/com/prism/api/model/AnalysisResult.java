package com.prism.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "Complete analysis result containing all detected ambiguities, entities, and generated artifacts")
public class AnalysisResult {
    
    @JsonProperty("ambiguities")
    @Schema(description = "List of detected ambiguities in the requirement")
    private List<Ambiguity> ambiguities;
    
    @JsonProperty("entities")
    @Schema(description = "Extracted entities from the requirement")
    @NotNull
    private ExtractedEntities entities;
    
    @JsonProperty("uml_diagrams")
    @Schema(description = "Generated UML diagrams", nullable = true)
    private UmlDiagrams umlDiagrams;
    
    @JsonProperty("pseudocode")
    @Schema(description = "Generated pseudocode", nullable = true)
    private String pseudocode;
    
    @JsonProperty("test_cases")
    @Schema(description = "Generated test cases", nullable = true)
    private TestCases testCases;
    
    @JsonProperty("improved_requirements")
    @Schema(description = "Improved version of the requirements", nullable = true)
    private String improvedRequirements;
    
    @JsonProperty("completeness_analysis")
    @Schema(description = "Analysis of requirement completeness", nullable = true)
    private CompletenessAnalysis completenessAnalysis;
    
    @JsonProperty("user_story_validation")
    @Schema(description = "Validation result for user story format", nullable = true)
    private UserStoryValidation userStoryValidation;
    
    @JsonProperty("nfr_suggestions")
    @Schema(description = "Non-functional requirement suggestions", nullable = true)
    private List<NonFunctionalRequirement> nfrSuggestions;

    // Constructors
    public AnalysisResult() {}

    public AnalysisResult(List<Ambiguity> ambiguities, ExtractedEntities entities) {
        this.ambiguities = ambiguities;
        this.entities = entities;
    }

    // Getters and Setters
    public List<Ambiguity> getAmbiguities() {
        return ambiguities;
    }

    public void setAmbiguities(List<Ambiguity> ambiguities) {
        this.ambiguities = ambiguities;
    }

    public ExtractedEntities getEntities() {
        return entities;
    }

    public void setEntities(ExtractedEntities entities) {
        this.entities = entities;
    }

    public UmlDiagrams getUmlDiagrams() {
        return umlDiagrams;
    }

    public void setUmlDiagrams(UmlDiagrams umlDiagrams) {
        this.umlDiagrams = umlDiagrams;
    }

    public String getPseudocode() {
        return pseudocode;
    }

    public void setPseudocode(String pseudocode) {
        this.pseudocode = pseudocode;
    }

    public TestCases getTestCases() {
        return testCases;
    }

    public void setTestCases(TestCases testCases) {
        this.testCases = testCases;
    }

    public String getImprovedRequirements() {
        return improvedRequirements;
    }

    public void setImprovedRequirements(String improvedRequirements) {
        this.improvedRequirements = improvedRequirements;
    }

    public CompletenessAnalysis getCompletenessAnalysis() {
        return completenessAnalysis;
    }

    public void setCompletenessAnalysis(CompletenessAnalysis completenessAnalysis) {
        this.completenessAnalysis = completenessAnalysis;
    }

    public UserStoryValidation getUserStoryValidation() {
        return userStoryValidation;
    }

    public void setUserStoryValidation(UserStoryValidation userStoryValidation) {
        this.userStoryValidation = userStoryValidation;
    }

    public List<NonFunctionalRequirement> getNfrSuggestions() {
        return nfrSuggestions;
    }

    public void setNfrSuggestions(List<NonFunctionalRequirement> nfrSuggestions) {
        this.nfrSuggestions = nfrSuggestions;
    }
}