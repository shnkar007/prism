package com.prism.api.controller;

import com.prism.api.service.ConfigurationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/config")
@Tag(name = "Configuration API", description = "Configuration management endpoints")
@CrossOrigin(origins = "*")
public class ConfigController {

    private final ConfigurationService configService;

    @Autowired
    public ConfigController(ConfigurationService configService) {
        this.configService = configService;
    }

    @GetMapping
    @Operation(
        summary = "Get configuration",
        description = "Get current configuration status"
    )
    public ResponseEntity<Map<String, Object>> getConfig() {
        ConfigurationService.PrismConfig config = configService.getConfig();
        
        Map<String, Object> response = Map.of(
            "ai_configured", configService.isAiConfigured(),
            "provider", config.getLlm() != null ? config.getLlm().getProvider() : "none",
            "model", config.getLlm() != null ? config.getLlm().getModel() : "none",
            "has_api_key", config.getLlm() != null && config.getLlm().getApiKey() != null
        );
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api-key")
    @Operation(
        summary = "Set API key",
        description = "Configure AI provider API key"
    )
    public ResponseEntity<Map<String, String>> setApiKey(
            @Parameter(description = "AI provider (openai, gemini, azure, claude, ollama)", required = true)
            @RequestParam String provider,
            @Parameter(description = "API key", required = true)
            @RequestParam String apiKey) {
        
        configService.setApiKey(provider, apiKey);
        
        return ResponseEntity.ok(Map.of(
            "status", "success",
            "message", "API key configured for " + provider
        ));
    }

    @PostMapping("/model")
    @Operation(
        summary = "Set model",
        description = "Configure AI model"
    )
    public ResponseEntity<Map<String, String>> setModel(
            @Parameter(description = "Model name", required = true)
            @RequestParam String model) {
        
        configService.setModel(model);
        
        return ResponseEntity.ok(Map.of(
            "status", "success",
            "message", "Model configured: " + model
        ));
    }

    @GetMapping("/providers")
    @Operation(
        summary = "List supported providers",
        description = "Get list of supported AI providers"
    )
    public ResponseEntity<Map<String, Object>> getSupportedProviders() {
        Map<String, Object> providers = Map.of(
            "providers", Map.of(
                "openai", Map.of(
                    "name", "OpenAI",
                    "models", new String[]{"gpt-3.5-turbo", "gpt-4", "gpt-4-turbo"}
                ),
                "gemini", Map.of(
                    "name", "Google Gemini",
                    "models", new String[]{"gemini-pro", "gemini-pro-vision"}
                ),
                "azure", Map.of(
                    "name", "Azure OpenAI",
                    "models", new String[]{"gpt-3.5-turbo", "gpt-4"}
                ),
                "claude", Map.of(
                    "name", "Anthropic Claude",
                    "models", new String[]{"claude-3-haiku", "claude-3-sonnet", "claude-3-opus"}
                ),
                "ollama", Map.of(
                    "name", "Ollama (Local)",
                    "models", new String[]{"llama2", "mistral", "codellama"}
                )
            )
        );
        
        return ResponseEntity.ok(providers);
    }
}