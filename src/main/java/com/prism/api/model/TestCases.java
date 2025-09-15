package com.prism.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Generated test cases for the requirement")
public class TestCases {
    
    @JsonProperty("unit_tests")
    @Schema(description = "List of unit test cases")
    private List<String> unitTests;
    
    @JsonProperty("integration_tests")
    @Schema(description = "List of integration test cases")
    private List<String> integrationTests;
    
    @JsonProperty("acceptance_tests")
    @Schema(description = "List of acceptance test cases")
    private List<String> acceptanceTests;

    // Constructors
    public TestCases() {}

    public TestCases(List<String> unitTests, List<String> integrationTests, List<String> acceptanceTests) {
        this.unitTests = unitTests;
        this.integrationTests = integrationTests;
        this.acceptanceTests = acceptanceTests;
    }

    // Getters and Setters
    public List<String> getUnitTests() {
        return unitTests;
    }

    public void setUnitTests(List<String> unitTests) {
        this.unitTests = unitTests;
    }

    public List<String> getIntegrationTests() {
        return integrationTests;
    }

    public void setIntegrationTests(List<String> integrationTests) {
        this.integrationTests = integrationTests;
    }

    public List<String> getAcceptanceTests() {
        return acceptanceTests;
    }

    public void setAcceptanceTests(List<String> acceptanceTests) {
        this.acceptanceTests = acceptanceTests;
    }
}