package com.gigshield.controller;

import com.gigshield.model.PremiumRequest;
import com.gigshield.model.PremiumResponse;
import com.gigshield.service.PremiumCalculatorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/premium")
public class PremiumController {

    private final PremiumCalculatorService service;

    public PremiumController(PremiumCalculatorService service) {
        this.service = service;
    }

    @PostMapping("/calculate")
    public ResponseEntity<PremiumResponse> calculate(@Valid @RequestBody PremiumRequest request) {
        PremiumResponse response = service.calculate(request);
        return ResponseEntity.ok(response);
    }

    // Health check endpoint
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("GigShield API is running ✅");
    }
}