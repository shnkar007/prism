package com.prism.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "An ambiguity detected in the requirement with suggestions for improvement")
public class Ambiguity {
    
    @JsonProperty("text")
    @Schema(description = "The ambiguous text that was detected")
    @NotBlank
    private String text;
    
    @JsonProperty("reason")
    @Schema(description = "Explanation of why this text is considered ambiguous")
    @NotBlank
    private String reason;
    
    @JsonProperty("suggestions")
    @Schema(description = "List of suggestions to resolve the ambiguity")
    private List<String> suggestions;
    
    @JsonProperty("severity")
    @Schema(description = "Severity level of the ambiguity")
    @NotNull
    private AmbiguitySeverity severity;

    // Constructors
    public Ambiguity() {}

    public Ambiguity(String text, String reason, List<String> suggestions, AmbiguitySeverity severity) {
        this.text = text;
        this.reason = reason;
        this.suggestions = suggestions;
        this.severity = severity;
    }

    // Getters and Setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public AmbiguitySeverity getSeverity() {
        return severity;
    }

    public void setSeverity(AmbiguitySeverity severity) {
        this.severity = severity;
    }
}