package com.gigshield.model;

public class PremiumResponse {

    private String workerType;
    private String city;
    private double weeklyIncome;
    private int coverageWeeks;

    private double basePremiumPerWeek;
    private double cityRiskMultiplier;
    private double workerTypeMultiplier;
    private double finalWeeklyPremium;
    private double totalPremium;

    private double maxPayoutPerWeek;
    private double totalMaxPayout;

    private String coverageSummary;

    // Getters & Setters
    public String getWorkerType() { return workerType; }
    public void setWorkerType(String workerType) { this.workerType = workerType; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public double getWeeklyIncome() { return weeklyIncome; }
    public void setWeeklyIncome(double weeklyIncome) { this.weeklyIncome = weeklyIncome; }

    public int getCoverageWeeks() { return coverageWeeks; }
    public void setCoverageWeeks(int coverageWeeks) { this.coverageWeeks = coverageWeeks; }

    public double getBasePremiumPerWeek() { return basePremiumPerWeek; }
    public void setBasePremiumPerWeek(double base) { this.basePremiumPerWeek = base; }

    public double getCityRiskMultiplier() { return cityRiskMultiplier; }
    public void setCityRiskMultiplier(double m) { this.cityRiskMultiplier = m; }

    public double getWorkerTypeMultiplier() { return workerTypeMultiplier; }
    public void setWorkerTypeMultiplier(double m) { this.workerTypeMultiplier = m; }

    public double getFinalWeeklyPremium() { return finalWeeklyPremium; }
    public void setFinalWeeklyPremium(double p) { this.finalWeeklyPremium = p; }

    public double getTotalPremium() { return totalPremium; }
    public void setTotalPremium(double t) { this.totalPremium = t; }

    public double getMaxPayoutPerWeek() { return maxPayoutPerWeek; }
    public void setMaxPayoutPerWeek(double m) { this.maxPayoutPerWeek = m; }

    public double getTotalMaxPayout() { return totalMaxPayout; }
    public void setTotalMaxPayout(double t) { this.totalMaxPayout = t; }

    public String getCoverageSummary() { return coverageSummary; }
    public void setCoverageSummary(String s) { this.coverageSummary = s; }
}