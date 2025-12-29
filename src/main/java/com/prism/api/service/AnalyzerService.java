package com.prism.api.service;

import com.prism.api.model.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class AnalyzerService {

    private final List<Pattern> vagueTerms;
    private final Pattern passiveVoice;
    private final Pattern conditionalIncomplete;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final ConfigurationService configService;

    @Autowired
    public AnalyzerService(ConfigurationService configService) {
        this.configService = configService;
        this.webClient = WebClient.builder().build();
        this.objectMapper = new ObjectMapper();
        
        // Initialize regex patterns for built-in analysis
        this.vagueTerms = Arrays.asList(
            Pattern.compile("\\b(fast|quick|slow|easy|hard|user-friendly|robust|scalable|efficient)\\b", Pattern.CASE_INSENSITIVE),
            Pattern.compile("\\b(better|worse|good|bad|nice|great|awesome)\\b", Pattern.CASE_INSENSITIVE),
            Pattern.compile("\\b(many|few|some|several|various|multiple)\\b", Pattern.CASE_INSENSITIVE)
        );
        
        this.passiveVoice = Pattern.compile("\\b(should be|will be|must be|needs to be|ought to be)\\s+\\w+ed\\b", Pattern.CASE_INSENSITIVE);
        this.conditionalIncomplete = Pattern.compile("\\bif\\b.*\\bwithout\\b.*\\belse\\b", Pattern.CASE_INSENSITIVE);
    }

    public CompletableFuture<AnalysisResult> analyze(AnalysisRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Apply preset configurations
                applyPreset(request);
                
                // Perform built-in analysis
                List<Ambiguity> ambiguities = detectAmbiguities(request.getText());
                ExtractedEntities entities = extractEntities(request.getText());
                
                // Create base result
                AnalysisResult result = new AnalysisResult(ambiguities, entities);
                
                // Enhanced analysis with AI if configured
                if (configService.isAiConfigured()) {
                    try {
                        enhanceWithAi(request.getText(), result).join();
                    } catch (Exception e) {
                        System.err.println("⚠️  AI enhancement failed: " + e.getMessage());
                        System.err.println("   Continuing with built-in analysis only");
                    }
                }
                
                // Generate additional artifacts based on request
                if (request.isGenerateUml()) {
                    result.setUmlDiagrams(generateUmlDiagrams(entities));
                }
                
                if (request.isGeneratePseudocode()) {
                    result.setPseudocode(generatePseudocode(entities, request.getPseudocodeLanguage()));
                }
                
                if (request.isGenerateTests()) {
                    result.setTestCases(generateTestCases(entities));
                }
                
                if (request.isImproveRequirements()) {
                    result.setImprovedRequirements(generateImprovedRequirements(request.getText(), ambiguities));
                }
                
                if (request.isAnalyzeCompleteness()) {
                    result.setCompletenessAnalysis(analyzeCompleteness(request.getText(), entities));
                }
                
                if (request.isValidateStory()) {
                    result.setUserStoryValidation(validateUserStory(request.getText()));
                }
                
                if (request.isGenerateNfr()) {
                    result.setNfrSuggestions(generateNfrSuggestions(request.getText(), entities));
                }
                
                return result;
            } catch (Exception e) {
                throw new RuntimeException("Analysis failed: " + e.getMessage(), e);
            }
        });
    }

    private void applyPreset(AnalysisRequest request) {
        if (request.getPreset() != null) {
            switch (request.getPreset()) {
                case BASIC:
                    // Only basic analysis (already enabled by default)
                    break;
                case STANDARD:
                    request.setGenerateUml(true);
                    request.setGenerateTests(true);
                    request.setGeneratePseudocode(true);
                    break;
                case FULL:
                    request.setGenerateUml(true);
                    request.setGeneratePseudocode(true);
                    request.setGenerateTests(true);
                    request.setImproveRequirements(true);
                    request.setGenerateNfr(true);
                    request.setAnalyzeCompleteness(true);
                    request.setValidateStory(true);
                    break;
                case REPORT:
                    request.setAnalyzeCompleteness(true);
                    request.setValidateStory(true);
                    break;
            }
        }
    }

    private List<Ambiguity> detectAmbiguities(String text) {
        List<Ambiguity> ambiguities = new ArrayList<>();
        
        // Check for vague terms
        for (Pattern pattern : vagueTerms) {
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                String vagueWord = matcher.group();
                ambiguities.add(new Ambiguity(
                    vagueWord,
                    "Vague term that lacks specific criteria",
                    Arrays.asList(
                        "Define specific measurable criteria",
                        "Provide quantitative thresholds",
                        "Use precise technical terms"
                    ),
                    AmbiguitySeverity.MEDIUM
                ));
            }
        }
        
        // Check for passive voice
        Matcher passiveMatcher = passiveVoice.matcher(text);
        while (passiveMatcher.find()) {
            String passivePhrase = passiveMatcher.group();
            ambiguities.add(new Ambiguity(
                passivePhrase,
                "Passive voice hides who is responsible for the action",
                Arrays.asList(
                    "Specify who performs the action",
                    "Use active voice",
                    "Clarify responsibility"
                ),
                AmbiguitySeverity.HIGH
            ));
        }
        
        return ambiguities;
    }

    private ExtractedEntities extractEntities(String text) {
        List<String> actors = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        List<String> objects = new ArrayList<>();
        
        // Simple entity extraction (can be enhanced with NLP libraries)
        String[] words = text.toLowerCase().split("\\s+");
        
        // Extract common actors
        for (String word : words) {
            if (word.matches("(user|admin|customer|client|system|service|manager|employee)")) {
                if (!actors.contains(word)) {
                    actors.add(word);
                }
            }
        }
        
        // Extract common actions (verbs)
        for (String word : words) {
            if (word.matches("(login|register|create|delete|update|view|access|submit|process|send|receive)")) {
                if (!actions.contains(word)) {
                    actions.add(word);
                }
            }
        }
        
        // Extract common objects (nouns)
        for (String word : words) {
            if (word.matches("(account|profile|document|report|data|information|password|email|notification|form)")) {
                if (!objects.contains(word)) {
                    objects.add(word);
                }
            }
        }
        
        return new ExtractedEntities(actors, actions, objects);
    }

    private CompletableFuture<Void> enhanceWithAi(String text, AnalysisResult result) {
        return CompletableFuture.runAsync(() -> {
            // AI enhancement would be implemented here
            // This would call external AI services (OpenAI, etc.) based on configuration
            // For now, this is a placeholder for the AI integration
        });
    }

    private UmlDiagrams generateUmlDiagrams(ExtractedEntities entities) {
        StringBuilder sequenceDiagram = new StringBuilder();
        sequenceDiagram.append("@startuml\n");
        
        if (!entities.getActors().isEmpty() && !entities.getActions().isEmpty()) {
            String primaryActor = entities.getActors().get(0);
            sequenceDiagram.append("actor ").append(primaryActor).append("\n");
            sequenceDiagram.append("participant System\n");
            
            for (String action : entities.getActions()) {
                sequenceDiagram.append(primaryActor).append(" -> System: ").append(action).append("\n");
                sequenceDiagram.append("System --> ").append(primaryActor).append(": response\n");
            }
        }
        
        sequenceDiagram.append("@enduml\n");
        
        return new UmlDiagrams(sequenceDiagram.toString(), "", "");
    }

    private String generatePseudocode(ExtractedEntities entities, String language) {
        StringBuilder pseudocode = new StringBuilder();
        String lang = language != null ? language.toLowerCase() : "generic";
        
        if ("java".equals(lang)) {
            pseudocode.append("public class RequirementImplementation {\n");
            for (String action : entities.getActions()) {
                pseudocode.append("    public void ").append(action).append("() {\n");
                pseudocode.append("        // TODO: Implement ").append(action).append(" logic\n");
                pseudocode.append("    }\n\n");
            }
            pseudocode.append("}\n");
        } else {
            for (String action : entities.getActions()) {
                pseudocode.append("function ").append(action).append("() {\n");
                pseudocode.append("    // TODO: Implement ").append(action).append(" logic\n");
                pseudocode.append("}\n\n");
            }
        }
        
        return pseudocode.toString();
    }

    private TestCases generateTestCases(ExtractedEntities entities) {
        List<String> unitTests = new ArrayList<>();
        List<String> integrationTests = new ArrayList<>();
        List<String> acceptanceTests = new ArrayList<>();
        
        for (String action : entities.getActions()) {
            unitTests.add("Test " + action + " with valid input");
            unitTests.add("Test " + action + " with invalid input");
            integrationTests.add("Test " + action + " integration with system");
            acceptanceTests.add("User can successfully " + action);
        }
        
        return new TestCases(unitTests, integrationTests, acceptanceTests);
    }

    private String generateImprovedRequirements(String originalText, List<Ambiguity> ambiguities) {
        StringBuilder improved = new StringBuilder();
        improved.append("Improved Requirements:\n\n");
        improved.append(originalText);
        
        if (!ambiguities.isEmpty()) {
            improved.append("\n\nSuggested Improvements:\n");
            for (Ambiguity ambiguity : ambiguities) {
                improved.append("- Replace '").append(ambiguity.getText())
                        .append("' with more specific criteria\n");
            }
        }
        
        return improved.toString();
    }

    private CompletenessAnalysis analyzeCompleteness(String text, ExtractedEntities entities) {
        double score = 70.0; // Base score
        List<String> missing = new ArrayList<>();
        List<String> suggestions = new ArrayList<>();
        
        if (entities.getActors().isEmpty()) {
            missing.add("Actors/Users");
            score -= 15.0;
            suggestions.add("Specify who will use this feature");
        }
        
        if (entities.getActions().isEmpty()) {
            missing.add("Actions/Verbs");
            score -= 20.0;
            suggestions.add("Define what actions will be performed");
        }
        
        if (!text.toLowerCase().contains("so that")) {
            missing.add("Business Value");
            score -= 10.0;
            suggestions.add("Add 'so that' clause to explain business value");
        }
        
        return new CompletenessAnalysis(Math.max(0, score), missing, suggestions);
    }

    private UserStoryValidation validateUserStory(String text) {
        boolean hasAsA = text.toLowerCase().contains("as a");
        boolean hasIWant = text.toLowerCase().contains("i want");
        boolean hasSoThat = text.toLowerCase().contains("so that");
        
        boolean isValid = hasAsA && hasIWant && hasSoThat;
        double score = 0;
        
        if (hasAsA) score += 33.33;
        if (hasIWant) score += 33.33;
        if (hasSoThat) score += 33.33;
        
        String feedback = "User story format: ";
        if (isValid) {
            feedback += "Follows proper format (As a... I want... So that...)";
        } else {
            feedback += "Missing components: ";
            List<String> missing = new ArrayList<>();
            if (!hasAsA) missing.add("'As a' clause");
            if (!hasIWant) missing.add("'I want' clause");
            if (!hasSoThat) missing.add("'So that' clause");
            feedback += String.join(", ", missing);
        }
        
        return new UserStoryValidation(isValid, score, feedback);
    }

    private List<NonFunctionalRequirement> generateNfrSuggestions(String text, ExtractedEntities entities) {
        List<NonFunctionalRequirement> nfrs = new ArrayList<>();
        
        // Generate common NFRs based on content
        nfrs.add(new NonFunctionalRequirement(
            "Performance",
            "System should respond within 2 seconds for 95% of requests",
            "High"
        ));
        
        nfrs.add(new NonFunctionalRequirement(
            "Security",
            "All data transmission must be encrypted using TLS 1.3",
            "High"
        ));
        
        nfrs.add(new NonFunctionalRequirement(
            "Usability",
            "Interface should be accessible according to WCAG 2.1 AA standards",
            "Medium"
        ));
        
        if (entities.getActions().stream().anyMatch(action -> action.contains("login"))) {
            nfrs.add(new NonFunctionalRequirement(
                "Security",
                "Failed login attempts should be limited to 5 per hour",
                "High"
            ));
        }
        
        return nfrs;
    }
}