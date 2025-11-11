package com.medical.dosage.pediatric_dosage_calculator.controller;

import com.medical.dosage.pediatric_dosage_calculator.service.AiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin("*")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/generate")
    public Mono<ResponseEntity<String>> generate(@RequestBody AiRequest request) {
        return aiService.generateResponse(request.getPrompt())
                .map(response -> ResponseEntity.ok(response))
                .onErrorReturn(ResponseEntity.badRequest().body("Error en la petición a IA"));
    }

    public static class AiRequest {
        private String prompt;

        public String getPrompt() {
            return prompt;
        }

        public void setPrompt(String prompt) {
            this.prompt = prompt;
        }
    }
}