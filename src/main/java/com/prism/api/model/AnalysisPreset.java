package com.prism.api.model;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Analysis presets for quick configuration")
public enum AnalysisPreset {
    BASIC("basic"),
    STANDARD("standard"),
    FULL("full"),
    REPORT("report");

    private final String value;

    AnalysisPreset(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}