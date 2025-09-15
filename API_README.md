# PRISM API - AI-Powered Requirement Analyzer

A Spring Boot REST API version of the PRISM requirement analyzer, providing intelligent analysis of software requirements with AI-powered insights.

## Features

- **Requirement Analysis**: Detect ambiguities, extract entities, and analyze requirement quality
- **AI-Powered Enhancement**: Optional integration with AI providers (OpenAI, Gemini, Azure, Claude, Ollama)
- **Artifact Generation**: Generate UML diagrams, pseudocode, and test cases
- **User Story Validation**: Validate user story format and provide improvement suggestions
- **Completeness Analysis**: Analyze requirement completeness and identify gaps
- **Non-Functional Requirements**: Generate NFR suggestions based on functional requirements

## Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application:

```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

### API Documentation

Once running, access the interactive API documentation at:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI Spec: `http://localhost:8080/v3/api-docs`

## API Endpoints

### Analysis Endpoints

- `POST /api/v1/analyze` - Comprehensive requirement analysis
- `POST /api/v1/analyze/simple?text={requirement}` - Simple analysis with text parameter
- `POST /api/v1/analyze/preset/{preset}?text={requirement}` - Analysis with preset configuration
- `POST /api/v1/improve?text={requirement}` - Generate improved requirements
- `POST /api/v1/validate?text={requirement}` - Validate user story format

### Generation Endpoints

- `POST /api/v1/generate/uml?text={requirement}` - Generate UML diagrams
- `POST /api/v1/generate/pseudocode?text={requirement}&language={lang}` - Generate pseudocode
- `POST /api/v1/generate/tests?text={requirement}` - Generate test cases

### Configuration Endpoints

- `GET /api/v1/config` - Get current configuration
- `POST /api/v1/config/api-key?provider={provider}&apiKey={key}` - Set AI provider API key
- `POST /api/v1/config/model?model={model}` - Set AI model
- `GET /api/v1/config/providers` - List supported AI providers

### Health Check

- `GET /api/v1/health` - Health check and status

## Configuration

### AI Providers

The API supports multiple AI providers for enhanced analysis:

- **OpenAI**: GPT-3.5-turbo, GPT-4
- **Google Gemini**: gemini-pro
- **Azure OpenAI**: GPT models via Azure
- **Anthropic Claude**: Claude-3 models
- **Ollama**: Local models (llama2, mistral, etc.)

Configure via API endpoints or by editing `~/.prism/config.yaml`

### Analysis Presets

- **basic**: Ambiguity detection and entity extraction only
- **standard**: Includes UML, pseudocode, and test generation
- **full**: All features including improvement suggestions and NFRs
- **report**: Optimized for analysis reports

## Example Usage

### Basic Analysis

```bash
curl -X POST "http://localhost:8080/api/v1/analyze/simple" \
  -d "text=As a user, I want to login quickly so that I can access my account"
```

### Full Analysis with Preset

```bash
curl -X POST "http://localhost:8080/api/v1/analyze/preset/full" \
  -d "text=As a user, I want to login quickly so that I can access my account" \
  -d "language=java"
```

### Comprehensive Analysis

```bash
curl -X POST "http://localhost:8080/api/v1/analyze" \
  -H "Content-Type: application/json" \
  -d '{
    "text": "As a user, I want to login quickly so that I can access my account",
    "generateUml": true,
    "generatePseudocode": true,
    "generateTests": true,
    "improveRequirements": true,
    "analyzeCompleteness": true,
    "validateStory": true,
    "generateNfr": true,
    "pseudocodeLanguage": "java"
  }'
```

## Project Structure

```
src/main/java/com/prism/api/
├── PrismApiApplication.java       # Main Spring Boot application
├── controller/
│   ├── AnalysisController.java    # Analysis endpoints
│   └── ConfigController.java      # Configuration endpoints
├── service/
│   ├── AnalyzerService.java       # Core analysis logic
│   └── ConfigurationService.java  # Configuration management
└── model/                         # Data models and DTOs
    ├── AnalysisResult.java
    ├── AnalysisRequest.java
    ├── Ambiguity.java
    ├── ExtractedEntities.java
    └── ... (other models)
```

## Migration from Rust CLI

This Spring Boot API provides equivalent functionality to the original Rust CLI tool:

- **CLI Commands → REST Endpoints**: Each CLI command has corresponding REST endpoints
- **Analysis Logic**: Core analysis algorithms converted from Rust to Java
- **Configuration**: YAML-based configuration maintained with similar structure
- **AI Integration**: Support for same AI providers with HTTP clients
- **Output Formats**: JSON responses instead of CLI output formatting

## Development

### Building

```bash
mvn clean package
```

### Running Tests

```bash
mvn test
```

### Creating Docker Image

```bash
docker build -t prism-api .
```

## License

MIT License - see LICENSE file for details.