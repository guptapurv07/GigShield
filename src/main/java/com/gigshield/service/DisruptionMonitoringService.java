package com.gigshield.service;

import com.gigshield.model.Claim;
import com.gigshield.model.Policy;
import com.gigshield.model.Worker;
import com.gigshield.repository.ClaimRepository;
import com.gigshield.repository.PolicyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DisruptionMonitoringService {

    private final PolicyRepository policyRepository;
    private final ClaimRepository claimRepository;
    private final RegistrationService registrationService;

    public DisruptionMonitoringService(PolicyRepository policyRepository, 
                                       ClaimRepository claimRepository,
                                       RegistrationService registrationService) {
        this.policyRepository = policyRepository;
        this.claimRepository = claimRepository;
        this.registrationService = registrationService;
    }

    /**
     * Replicates a webhook receiver for weather/public APIs.
     * When a disruption occurs in a city, we identify all impacted workers and trigger payouts.
     */
    public String triggerDisruption(String type, String city, int severity) {
        // Validate triggers
        if (!isValidTrigger(type, severity)) {
            return "Disruption ignored. Severity threshold not met for type: " + type;
        }

        // Get all active policies
        List<Policy> activePolicies = policyRepository.findByIsActiveTrue();
        int claimsGenerated = 0;

        for (Policy policy : activePolicies) {
            try {
                Worker worker = registrationService.getWorkerById(policy.getWorkerId());
                
                // If worker is in the affected city
                if (worker.getCity().equalsIgnoreCase(city)) {
                    // Pre-claim validation (Simulating intelligent fraud detection where 5% might be rejected if GPS mock failed)
                    if (isFraudulentClaimSimulated(worker)) {
                        continue; 
                    }

                    // Trigger Zero-Touch Claim
                    Claim claim = new Claim(
                            policy.getId(),
                            type,
                            policy.getMaxPayoutCoverage(),
                            "PAID" // Instant payout
                    );
                    claimRepository.save(claim);
                    claimsGenerated++;
                    
                    // Mark policy as used/inactive for this week to prevent duplicate claims
                    policy.setActive(false);
                    policyRepository.save(policy);
                }
            } catch (Exception e) {
                // log and continue
            }
        }

        return String.format("Disruption '%s' verified in %s. Automatically processed %d zero-touch claims.", type, city, claimsGenerated);
    }

    private boolean isValidTrigger(String type, int severity) {
        switch (type.toUpperCase()) {
            case "EXTREME_HEAT":
                return severity >= 45; // e.g. 45 degrees
            case "HEAVY_RAINFALL":
                return severity >= 50; // e.g. 50 mm
            case "SEVERE_POLLUTION":
                return severity >= 400; // e.g. AQI 400
            case "SOCIAL_CURFEW":
                return severity >= 1; // binary alert level
            default:
                return false;
        }
    }

    private boolean isFraudulentClaimSimulated(Worker worker) {
        // Simulates an ML cross-check for GPS spoofing vs delivery platform logs
        return Math.random() < 0.05; // 5% chance the worker is verified as offline and ineligible
    }
}
