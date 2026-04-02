package com.gigshield.service;

import com.gigshield.dto.WorkerRegistrationRequest;
import com.gigshield.dto.WorkerRegistrationResponse;
import com.gigshield.model.Worker;
import com.gigshield.repository.WorkerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Core business logic for the Worker Registration Process.
 *
 * Responsibilities:
 *  1. Validate uniqueness of email, phone, and Aadhaar
 *  2. Hash the password securely (BCrypt)
 *  3. Persist the worker record
 *  4. Calculate an estimated weekly premium at registration time
 */
@Service
public class RegistrationService {

    private final WorkerRepository workerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Premium rate: 2% of average weekly earnings (GigShield's weekly pricing model)
    private static final double PREMIUM_RATE = 0.02;

    // City-based risk multipliers (higher disruption probability = higher premium)
    private static final java.util.Map<String, Double> CITY_RISK_MULTIPLIER = java.util.Map.of(
        "mumbai",   1.3,   // high rainfall risk
        "chennai",  1.25,
        "kolkata",  1.2,
        "delhi",    1.1,
        "bengaluru", 1.05,
        "hyderabad", 1.05
    );

    public RegistrationService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Registers a new gig worker.
     *
     * @param request validated registration payload
     * @return registration response with workerId, status, and estimated premium
     * @throws IllegalArgumentException if email/phone/aadhaar already exists
     */
    public WorkerRegistrationResponse registerWorker(WorkerRegistrationRequest request) {

        // ── Step 1: Duplicate checks ──────────────────────────────
        if (workerRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                "An account with this email already exists: " + request.getEmail()
            );
        }
        if (workerRepository.existsByPhone(request.getPhone())) {
            throw new IllegalArgumentException(
                "An account with this phone number already exists: " + request.getPhone()
            );
        }
        if (workerRepository.existsByAadhaarNumber(request.getAadhaarNumber())) {
            throw new IllegalArgumentException(
                "An account linked to this Aadhaar number already exists."
            );
        }

        // ── Step 2: Hash password ─────────────────────────────────
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        // ── Step 3: Build & save Worker entity ───────────────────
        Worker worker = new Worker(
            request.getFullName(),
            request.getEmail(),
            hashedPassword,
            request.getPhone(),
            request.getAadhaarNumber(),
            request.getPlatform(),
            request.getCity(),
            request.getVehicleType(),
            request.getAverageWeeklyEarnings()
        );

        Worker savedWorker = workerRepository.save(worker);

        // ── Step 4: Calculate estimated weekly premium ────────────
        double estimatedPremium = calculateWeeklyPremium(
            request.getAverageWeeklyEarnings(),
            request.getCity(),
            request.getVehicleType()
        );

        // ── Step 5: Build & return response ──────────────────────
        return new WorkerRegistrationResponse(
            savedWorker.getId(),
            savedWorker.getFullName(),
            savedWorker.getEmail(),
            savedWorker.getPhone(),
            savedWorker.getPlatform(),
            savedWorker.getCity(),
            savedWorker.getVehicleType(),
            savedWorker.getAverageWeeklyEarnings(),
            estimatedPremium,
            savedWorker.getStatus().name(),
            savedWorker.getRegisteredAt(),
            "Registration successful! Your account is pending KYC verification. " +
            "Estimated weekly premium: ₹" + String.format("%.2f", estimatedPremium)
        );
    }

    /**
     * Calculates the weekly premium based on:
     *  - Worker's average weekly earnings (base rate: 2%)
     *  - City risk multiplier (rain/disruption history)
     *  - Vehicle type (cycle/foot workers get a small discount — they take on more weather risk)
     *
     * @param weeklyEarnings average weekly earnings in ₹
     * @param city           delivery city (lowercase matched)
     * @param vehicleType    BIKE / CYCLE / FOOT
     * @return estimated weekly premium in ₹
     */
    public double calculateWeeklyPremium(double weeklyEarnings, String city, String vehicleType) {
        double basePremium = weeklyEarnings * PREMIUM_RATE;

        // Apply city-based risk multiplier (default 1.0 if city not in map)
        double cityMultiplier = CITY_RISK_MULTIPLIER
            .getOrDefault(city.toLowerCase().trim(), 1.0);

        // Vehicle type adjustment
        double vehicleMultiplier = switch (vehicleType.toUpperCase()) {
            case "CYCLE" -> 1.1;  // slightly higher risk (more exposed)
            case "FOOT"  -> 1.15; // highest exposure
            default      -> 1.0;  // BIKE
        };

        double premium = basePremium * cityMultiplier * vehicleMultiplier;

        // Round to 2 decimal places
        return Math.round(premium * 100.0) / 100.0;
    }

    /**
     * Fetches a worker by their ID.
     *
     * @param workerId the worker's database ID
     * @return Worker entity
     * @throws IllegalArgumentException if no worker found
     */
    public Worker getWorkerById(Long workerId) {
        return workerRepository.findById(workerId)
            .orElseThrow(() -> new IllegalArgumentException("Worker not found with ID: " + workerId));
    }
}
