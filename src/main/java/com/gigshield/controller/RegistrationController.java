package com.gigshield.controller;

import com.gigshield.dto.WorkerRegistrationRequest;
import com.gigshield.dto.WorkerRegistrationResponse;
import com.gigshield.model.Worker;
import com.gigshield.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * REST Controller for Worker Registration endpoints.
 *
 * Endpoints:
 *   POST   /api/v1/workers/register      — Register a new gig worker
 *   GET    /api/v1/workers/{id}          — Get worker details by ID
 *   GET    /api/v1/workers/health        — Health check
 */
@RestController
@RequestMapping("/api/v1/workers")
@CrossOrigin(origins = "*")  // Allow all origins for demo purposes
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    /**
     * Register a new gig worker.
     *
     * POST /api/v1/workers/register
     * Body: WorkerRegistrationRequest (JSON)
     *
     * Returns 201 Created on success.
     */
    @PostMapping("/register")
    public ResponseEntity<WorkerRegistrationResponse> registerWorker(
            @Valid @RequestBody WorkerRegistrationRequest request) {

        WorkerRegistrationResponse response = registrationService.registerWorker(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get worker details by ID.
     *
     * GET /api/v1/workers/{id}
     *
     * Returns 200 OK with worker info (no sensitive fields).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getWorker(@PathVariable Long id) {
        Worker worker = registrationService.getWorkerById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("workerId", worker.getId());
        response.put("fullName", worker.getFullName());
        response.put("email", worker.getEmail());
        response.put("phone", worker.getPhone());
        response.put("platform", worker.getPlatform());
        response.put("city", worker.getCity());
        response.put("vehicleType", worker.getVehicleType());
        response.put("averageWeeklyEarnings", worker.getAverageWeeklyEarnings());
        response.put("status", worker.getStatus());
        response.put("registeredAt", worker.getRegisteredAt());

        return ResponseEntity.ok(response);
    }

    /**
     * Simple health check endpoint.
     *
     * GET /api/v1/workers/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "GigShield Registration Service");
        response.put("version", "1.0.0");
        return ResponseEntity.ok(response);
    }

    // ── Exception Handlers ────────────────────────────────────────

    /**
     * Handles @Valid validation failures — returns field-by-field error messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", "VALIDATION_FAILED");
        response.put("errors", fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handles business logic errors (duplicate email/phone/aadhaar).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBusinessErrors(IllegalArgumentException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "ERROR");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
