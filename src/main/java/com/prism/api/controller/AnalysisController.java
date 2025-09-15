package com.prism.api.controller;

import com.prism.api.model.*;
import com.prism.api.service.AnalyzerService;
import com.prism.api.service.ConfigurationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "PRISM Analysis API", description = "AI-Powered Requirement Analysis endpoints")
@CrossOrigin(origins = "*")
public class AnalysisController {

    private final AnalyzerService analyzerService;
    private final ConfigurationService configService;

    @Autowired
    public AnalysisController(AnalyzerService analyzerService, ConfigurationService configService) {
        this.analyzerService = analyzerService;
        this.configService = configService;
    }

    @PostMapping("/analyze")
    @Operation(
        summary = "Analyze requirements",
        description = "Analyze requirement text for ambiguities, entities, and generate artifacts based on the request configuration"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Analysis completed successfully",
        content = @Content(schema = @Schema(implementation = AnalysisResult.class))
    )
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "500", description = "Analysis failed")
    public CompletableFuture<ResponseEntity<AnalysisResult>> analyzeRequirements(
            @Valid @RequestBody AnalysisRequest request) {
        
        return analyzerService.analyze(request)
                .thenApply(result -> ResponseEntity.ok(result))
                .exceptionally(throwable -> 
                    ResponseEntity.internalServerError().build()
                );
    }

    @PostMapping("/analyze/simple")
    @Operation(
        summary = "Simple requirement analysis",
        description = "Analyze requirement text with basic configuration (ambiguities and entities only)"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Analysis completed successfully",
        content = @Content(schema = @Schema(implementation = AnalysisResult.class))
    )
    public CompletableFuture<ResponseEntity<AnalysisResult>> analyzeSimple(
            @Parameter(description = "Requirement text to analyze", required = true)
            @RequestParam String text) {
        
        AnalysisRequest request = new AnalysisRequest(text);
        return analyzeRequirements(request);
    }

    @PostMapping("/analyze/preset/{preset}")
    @Operation(
        summary = "Analyze with preset configuration",
        description = "Analyze requirement text using predefined analysis presets"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Analysis completed successfully",
        content = @Content(schema = @Schema(implementation = AnalysisResult.class))
    )
    public CompletableFuture<ResponseEntity<AnalysisResult>> analyzeWithPreset(
            @Parameter(description = "Analysis preset", required = true)
            @PathVariable AnalysisPreset preset,
            @Parameter(description = "Requirement text to analyze", required = true)
            @RequestParam String text,
            @Parameter(description = "Programming language for pseudocode")
            @RequestParam(required = false) String language) {
        
        AnalysisRequest request = new AnalysisRequest(text);
        request.setPreset(preset);
        if (language != null) {
            request.setPseudocodeLanguage(language);
        }
        
        return analyzeRequirements(request);
    }

    @PostMapping("/improve")
    @Operation(
        summary = "Improve requirements",
        description = "Generate improved version of requirements by fixing detected ambiguities"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Improvement completed successfully",
        content = @Content(schema = @Schema(implementation = AnalysisResult.class))
    )
    public CompletableFuture<ResponseEntity<AnalysisResult>> improveRequirements(
            @Parameter(description = "Requirement text to improve", required = true)
            @RequestParam String text) {
        
        AnalysisRequest request = new AnalysisRequest(text);
        request.setImproveRequirements(true);
        
        return analyzeRequirements(request);
    }

    @PostMapping("/validate")
    @Operation(
        summary = "Validate user story",
        description = "Validate user story format and provide feedback"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Validation completed successfully",
        content = @Content(schema = @Schema(implementation = UserStoryValidation.class))
    )
    public CompletableFuture<ResponseEntity<UserStoryValidation>> validateUserStory(
            @Parameter(description = "User story text to validate", required = true)
            @RequestParam String text) {
        
        AnalysisRequest request = new AnalysisRequest(text);
        request.setValidateStory(true);
        
        return analyzerService.analyze(request)
                .thenApply(result -> ResponseEntity.ok(result.getUserStoryValidation()))
                .exceptionally(throwable -> 
                    ResponseEntity.internalServerError().build()
                );
    }

    @PostMapping("/generate/uml")
    @Operation(
        summary = "Generate UML diagrams",
        description = "Generate UML diagrams from requirement text"
    )
    @ApiResponse(
        responseCode = "200",
        description = "UML generation completed successfully",
        content = @Content(schema = @Schema(implementation = UmlDiagrams.class))
    )
    public CompletableFuture<ResponseEntity<UmlDiagrams>> generateUml(
            @Parameter(description = "Requirement text", required = true)
            @RequestParam String text) {
        
        AnalysisRequest request = new AnalysisRequest(text);
        request.setGenerateUml(true);
        
        return analyzerService.analyze(request)
                .thenApply(result -> ResponseEntity.ok(result.getUmlDiagrams()))
                .exceptionally(throwable -> 
                    ResponseEntity.internalServerError().build()
                );
    }

    @PostMapping("/generate/pseudocode")
    @Operation(
        summary = "Generate pseudocode",
        description = "Generate pseudocode from requirement text"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Pseudocode generation completed successfully"
    )
    public CompletableFuture<ResponseEntity<String>> generatePseudocode(
            @Parameter(description = "Requirement text", required = true)
            @RequestParam String text,
            @Parameter(description = "Programming language style")
            @RequestParam(required = false, defaultValue = "generic") String language) {
        
        AnalysisRequest request = new AnalysisRequest(text);
        request.setGeneratePseudocode(true);
        request.setPseudocodeLanguage(language);
        
        return analyzerService.analyze(request)
                .thenApply(result -> ResponseEntity.ok(result.getPseudocode()))
                .exceptionally(throwable -> 
                    ResponseEntity.internalServerError().build()
                );
    }

    @PostMapping("/generate/tests")
    @Operation(
        summary = "Generate test cases",
        description = "Generate test cases from requirement text"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Test case generation completed successfully",
        content = @Content(schema = @Schema(implementation = TestCases.class))
    )
    public CompletableFuture<ResponseEntity<TestCases>> generateTests(
            @Parameter(description = "Requirement text", required = true)
            @RequestParam String text) {
        
        AnalysisRequest request = new AnalysisRequest(text);
        request.setGenerateTests(true);
        
        return analyzerService.analyze(request)
                .thenApply(result -> ResponseEntity.ok(result.getTestCases()))
                .exceptionally(throwable -> 
                    ResponseEntity.internalServerError().build()
                );
    }

    @GetMapping("/health")
    @Operation(
        summary = "Health check",
        description = "Check API health and configuration status"
    )
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> health = Map.of(
            "status", "UP",
            "version", "2.0.0",
            "ai_configured", configService.isAiConfigured()
        );
        return ResponseEntity.ok(health);
    }
}