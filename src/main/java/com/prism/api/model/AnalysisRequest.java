package com.prism.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "Request for analyzing requirements")
public class AnalysisRequest {
    
    @JsonProperty("text")
    @Schema(description = "Requirement text to analyze", example = "As a user, I want to login quickly so that I can access my account")
    @NotBlank(message = "Requirement text cannot be blank")
    private String text;
    
    @JsonProperty("generate_uml")
    @Schema(description = "Whether to generate UML diagrams", defaultValue = "false")
    private boolean generateUml = false;
    
    @JsonProperty("generate_pseudocode")
    @Schema(description = "Whether to generate pseudocode", defaultValue = "false")
    private boolean generatePseudocode = false;
    
    @JsonProperty("generate_tests")
    @Schema(description = "Whether to generate test cases", defaultValue = "false")
    private boolean generateTests = false;
    
    @JsonProperty("improve_requirements")
    @Schema(description = "Whether to generate improved requirements", defaultValue = "false")
    private boolean improveRequirements = false;
    
    @JsonProperty("analyze_completeness")
    @Schema(description = "Whether to analyze completeness", defaultValue = "false")
    private boolean analyzeCompleteness = false;
    
    @JsonProperty("validate_story")
    @Schema(description = "Whether to validate user story format", defaultValue = "false")
    private boolean validateStory = false;
    
    @JsonProperty("generate_nfr")
    @Schema(description = "Whether to generate NFR suggestions", defaultValue = "false")
    private boolean generateNfr = false;
    
    @JsonProperty("pseudocode_language")
    @Schema(description = "Programming language for pseudocode generation", example = "java")
    private String pseudocodeLanguage;
    
    @JsonProperty("preset")
    @Schema(description = "Analysis preset to use")
    private AnalysisPreset preset;

    // Constructors
    public AnalysisRequest() {}

    public AnalysisRequest(String text) {
        this.text = text;
    }

    // Getters and Setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isGenerateUml() {
        return generateUml;
    }

    public void setGenerateUml(boolean generateUml) {
        this.generateUml = generateUml;
    }

    public boolean isGeneratePseudocode() {
        return generatePseudocode;
    }

    public void setGeneratePseudocode(boolean generatePseudocode) {
        this.generatePseudocode = generatePseudocode;
    }

    public boolean isGenerateTests() {
        return generateTests;
    }

    public void setGenerateTests(boolean generateTests) {
        this.generateTests = generateTests;
    }

    public boolean isImproveRequirements() {
        return improveRequirements;
    }

    public void setImproveRequirements(boolean improveRequirements) {
        this.improveRequirements = improveRequirements;
    }

    public boolean isAnalyzeCompleteness() {
        return analyzeCompleteness;
    }

    public void setAnalyzeCompleteness(boolean analyzeCompleteness) {
        this.analyzeCompleteness = analyzeCompleteness;
    }

    public boolean isValidateStory() {
        return validateStory;
    }

    public void setValidateStory(boolean validateStory) {
        this.validateStory = validateStory;
    }

    public boolean isGenerateNfr() {
        return generateNfr;
    }

    public void setGenerateNfr(boolean generateNfr) {
        this.generateNfr = generateNfr;
    }

    public String getPseudocodeLanguage() {
        return pseudocodeLanguage;
    }

    public void setPseudocodeLanguage(String pseudocodeLanguage) {
        this.pseudocodeLanguage = pseudocodeLanguage;
    }

    public AnalysisPreset getPreset() {
        return preset;
    }

    public void setPreset(AnalysisPreset preset) {
        this.preset = preset;
    }
}