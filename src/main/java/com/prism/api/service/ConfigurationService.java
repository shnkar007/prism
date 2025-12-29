package com.prism.api.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConfigurationService {

    private final ObjectMapper yamlMapper;
    private PrismConfig config;
    
    @Value("${prism.config.path:#{systemProperties['user.home']}/.prism/config.yaml}")
    private String configPath;

    public ConfigurationService() {
        this.yamlMapper = new ObjectMapper(new YAMLFactory());
        loadConfiguration();
    }

    public boolean isAiConfigured() {
        return config != null && 
               config.getLlm() != null && 
               config.getLlm().getApiKey() != null && 
               !config.getLlm().getApiKey().isEmpty();
    }

    public PrismConfig getConfig() {
        return config;
    }

    public void setApiKey(String provider, String apiKey) {
        if (config == null) {
            config = new PrismConfig();
        }
        if (config.getLlm() == null) {
            config.setLlm(new LlmConfig());
        }
        
        config.getLlm().setProvider(provider);
        config.getLlm().setApiKey(apiKey);
        saveConfiguration();
    }

    public void setModel(String model) {
        if (config == null) {
            config = new PrismConfig();
        }
        if (config.getLlm() == null) {
            config.setLlm(new LlmConfig());
        }
        
        config.getLlm().setModel(model);
        saveConfiguration();
    }

    private void loadConfiguration() {
        try {
            File configFile = new File(configPath);
            if (configFile.exists()) {
                config = yamlMapper.readValue(configFile, PrismConfig.class);
            } else {
                config = new PrismConfig();
                // Create default config
                config.setLlm(new LlmConfig());
                saveConfiguration();
            }
        } catch (IOException e) {
            System.err.println("Failed to load configuration: " + e.getMessage());
            config = new PrismConfig();
            config.setLlm(new LlmConfig());
        }
    }

    private void saveConfiguration() {
        try {
            File configFile = new File(configPath);
            configFile.getParentFile().mkdirs();
            yamlMapper.writeValue(configFile, config);
        } catch (IOException e) {
            System.err.println("Failed to save configuration: " + e.getMessage());
        }
    }

    // Configuration classes
    public static class PrismConfig {
        private LlmConfig llm;

        public LlmConfig getLlm() {
            return llm;
        }

        public void setLlm(LlmConfig llm) {
            this.llm = llm;
        }
    }

    public static class LlmConfig {
        private String provider = "openai";
        private String model = "gpt-3.5-turbo";
        private String apiKey;
        private String baseUrl;

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }
    }
}