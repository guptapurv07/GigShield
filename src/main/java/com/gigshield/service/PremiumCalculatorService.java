package com.gigshield.service;

import com.gigshield.model.PremiumRequest;
import com.gigshield.model.PremiumResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PremiumCalculatorService {

    // Base premium = 3% of weekly income
    private static final double BASE_PREMIUM_RATE = 0.03;

    // Max payout = 70% of weekly income per disruption week
    private static final double MAX_PAYOUT_RATE = 0.70;

    // City risk multipliers (higher rainfall / disruption risk = higher premium)
    private static final Map<String, Double> CITY_RISK = Map.of(
        "MUMBAI",    1.30,   // Very high monsoon risk
        "CHENNAI",   1.25,   // High cyclone + rain risk
        "DELHI",     1.10,   // Moderate risk + heatwave
        "BENGALURU", 1.05,   // Moderate risk
        "OTHER",     1.00    // Baseline
    );

    // Worker type multipliers (delivery = highest exposure)
    private static final Map<String, Double> WORKER_TYPE_RISK = Map.of(
        "DELIVERY",   1.20,
        "RIDE_SHARE", 1.10,
        "FREELANCE",  1.00
    );

    public PremiumResponse calculate(PremiumRequest request) {

        double cityMultiplier   = CITY_RISK.getOrDefault(
            request.getCity().toUpperCase(), 1.00);
        double workerMultiplier = WORKER_TYPE_RISK.getOrDefault(
            request.getWorkerType().toUpperCase(), 1.00);

        double basePremium        = request.getWeeklyIncome() * BASE_PREMIUM_RATE;
        double finalWeeklyPremium = round(basePremium * cityMultiplier * workerMultiplier);
        double totalPremium       = round(finalWeeklyPremium * request.getCoverageWeeks());

        double maxPayoutPerWeek = round(request.getWeeklyIncome() * MAX_PAYOUT_RATE);
        double totalMaxPayout   = round(maxPayoutPerWeek * request.getCoverageWeeks());

        String summary = String.format(
            "Worker pays ₹%.2f/week for %d week(s). " +
            "Max payout per disrupted week: ₹%.2f. Total coverage: ₹%.2f.",
            finalWeeklyPremium, request.getCoverageWeeks(),
            maxPayoutPerWeek, totalMaxPayout
        );

        PremiumResponse response = new PremiumResponse();
        response.setWorkerType(request.getWorkerType().toUpperCase());
        response.setCity(request.getCity().toUpperCase());
        response.setWeeklyIncome(request.getWeeklyIncome());
        response.setCoverageWeeks(request.getCoverageWeeks());
        response.setBasePremiumPerWeek(round(basePremium));
        response.setCityRiskMultiplier(cityMultiplier);
        response.setWorkerTypeMultiplier(workerMultiplier);
        response.setFinalWeeklyPremium(finalWeeklyPremium);
        response.setTotalPremium(totalPremium);
        response.setMaxPayoutPerWeek(maxPayoutPerWeek);
        response.setTotalMaxPayout(totalMaxPayout);
        response.setCoverageSummary(summary);

        return response;
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}