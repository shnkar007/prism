package com.prism.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "UML diagrams generated from the requirement")
public class UmlDiagrams {
    
    @JsonProperty("sequence_diagram")
    @Schema(description = "UML sequence diagram")
    private String sequenceDiagram;
    
    @JsonProperty("class_diagram")
    @Schema(description = "UML class diagram")
    private String classDiagram;
    
    @JsonProperty("use_case_diagram")
    @Schema(description = "UML use case diagram")
    private String useCaseDiagram;

    // Constructors
    public UmlDiagrams() {}

    public UmlDiagrams(String sequenceDiagram, String classDiagram, String useCaseDiagram) {
        this.sequenceDiagram = sequenceDiagram;
        this.classDiagram = classDiagram;
        this.useCaseDiagram = useCaseDiagram;
    }

    // Getters and Setters
    public String getSequenceDiagram() {
        return sequenceDiagram;
    }

    public void setSequenceDiagram(String sequenceDiagram) {
        this.sequenceDiagram = sequenceDiagram;
    }

    public String getClassDiagram() {
        return classDiagram;
    }

    public void setClassDiagram(String classDiagram) {
        this.classDiagram = classDiagram;
    }

    public String getUseCaseDiagram() {
        return useCaseDiagram;
    }

    public void setUseCaseDiagram(String useCaseDiagram) {
        this.useCaseDiagram = useCaseDiagram;
    }
}