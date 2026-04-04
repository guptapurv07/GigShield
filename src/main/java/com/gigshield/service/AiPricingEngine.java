package com.gigshield.service;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AiPricingEngine {

    // Represents hyperlocal zones that have a historical low risk of water logging or disruption.
    // If a worker is in these zones, we intelligently discount the premium.
    private static final List<String> SAFE_ZONES = List.of("andheri_east", "jayanagar", "connaught_place");

    /**
     * Uses predictive modeling (simulated) to adjust the base premium.
     * 
     * @param basePremium          The initially calculated premium
     * @param city                 The city the worker operates in
     * @param hyperLocalZone       Specific area the worker usually operates in
     * @return The AI adjusted dynamic premium
     */
    public double adjustPremiumDynamically(double basePremium, String city, String hyperLocalZone) {
        double adjustedPremium = basePremium;

        // 1. Hyper-local risk factor (Machine Learning simulated):
        // If the driver is in a safe zone, reduce the premium by ₹2 per week
        if (hyperLocalZone != null && SAFE_ZONES.contains(hyperLocalZone.toLowerCase().trim())) {
            adjustedPremium -= 2.0;
            // Prevent negative premiums
            if (adjustedPremium < 1.0) {
                adjustedPremium = 1.0;
            }
        }

        // 2. Predictive weather modeling (Mock):
        // If we predict heavy rain this week for Mumbai, we can slightly increase risk dynamically
        if ("MUMBAI".equalsIgnoreCase(city) && isMonsoonSeasonSimulated()) {
            adjustedPremium *= 1.10; // 10% dynamic surcharge for predicted weather anomaly
        }

        return Math.round(adjustedPremium * 100.0) / 100.0;
    }

    private boolean isMonsoonSeasonSimulated() {
        return Math.random() > 0.5; // Simulate if AI predicts severe weather approaching based on current patterns
    }
}
