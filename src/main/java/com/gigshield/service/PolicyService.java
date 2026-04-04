package com.gigshield.service;

import com.gigshield.model.Policy;
import com.gigshield.model.Worker;
import com.gigshield.repository.PolicyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final RegistrationService registrationService;
    private final AiPricingEngine aiPricingEngine;

    public PolicyService(PolicyRepository policyRepository, 
                         RegistrationService registrationService,
                         AiPricingEngine aiPricingEngine) {
        this.policyRepository = policyRepository;
        this.registrationService = registrationService;
        this.aiPricingEngine = aiPricingEngine;
    }

    /**
     * Issues a weekly policy for the worker, utilizing the dynamic AI pricing engine.
     */
    public Policy issueWeeklyPolicy(Long workerId, String hyperLocalZone) {
        Worker worker = registrationService.getWorkerById(workerId);

        // Get base premium from registration logic
        double basePremium = registrationService.calculateWeeklyPremium(
                worker.getAverageWeeklyEarnings(), 
                worker.getCity(), 
                worker.getVehicleType()
        );

        // Adjust dynamically based on AI hyper-local risk assessment
        double dynamicPremium = aiPricingEngine.adjustPremiumDynamically(basePremium, worker.getCity(), hyperLocalZone);

        // Deactivate old policies for this worker if needed (ensuring only 1 active policy)
        List<Policy> activePolicies = policyRepository.findByWorkerIdAndIsActiveTrue(workerId);
        for (Policy p : activePolicies) {
            p.setActive(false);
            policyRepository.save(p);
        }

        // Max payout is 70% of weekly earnings (from Phase 1 logic)
        double maxPayout = worker.getAverageWeeklyEarnings() * 0.70;

        Policy newPolicy = new Policy(
                workerId,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7), // Weekly policy
                dynamicPremium,
                maxPayout,
                true
        );

        return policyRepository.save(newPolicy);
    }
}
