package com.prism.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Validation result for user story format")
public class UserStoryValidation {
    
    @JsonProperty("is_valid")
    @Schema(description = "Whether the user story follows proper format")
    private boolean isValid;
    
    @JsonProperty("score")
    @Schema(description = "Quality score of the user story (0-100)")
    private double score;
    
    @JsonProperty("feedback")
    @Schema(description = "Feedback on how to improve the user story")
    private String feedback;

    // Constructors
    public UserStoryValidation() {}

    public UserStoryValidation(boolean isValid, double score, String feedback) {
        this.isValid = isValid;
        this.score = score;
        this.feedback = feedback;
    }

    // Getters and Setters
    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}