package com.gigshield.controller;

import com.gigshield.dto.DisruptionTriggerRequest;
import com.gigshield.service.DisruptionMonitoringService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/webhooks")
public class DisruptionWebhookController {

    private final DisruptionMonitoringService monitoringService;

    public DisruptionWebhookController(DisruptionMonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @PostMapping("/disruption")
    public ResponseEntity<Map<String, String>> simulateDisruption(
            @Valid @RequestBody DisruptionTriggerRequest request) {
        
        String result = monitoringService.triggerDisruption(
                request.getType(), 
                request.getCity(), 
                request.getSeverity()
        );
        
        return ResponseEntity.ok(Map.of(
                "status", "SUCCESS",
                "message", result
        ));
    }
}
