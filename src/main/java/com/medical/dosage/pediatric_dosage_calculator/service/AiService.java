package com.medical.dosage.pediatric_dosage_calculator.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AiService {

    @Value("${google.ai.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.create();

    public Mono<String> generateResponse(String prompt) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey;

        String requestBody = """
            {
                "contents": [
                    {
                        "parts": [
                            {
                                "text": "%s"
                            }
                        ]
                    }
                ]
            }
            """.formatted(prompt);

        return webClient.post()
                .uri(url)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .map(this::extractTextFromResponse);
    }

    private String extractTextFromResponse(String response) {
        // Simplificado: extrae el texto de la respuesta JSON de Gemini
        // En producción, usa una librería como Jackson para parsear correctamente
        if (response.contains("\"text\":")) {
            int start = response.indexOf("\"text\": \"") + 9;
            int end = response.indexOf("\"", start);
            return response.substring(start, end);
        }
        return "Error al procesar respuesta de IA";
    }
}