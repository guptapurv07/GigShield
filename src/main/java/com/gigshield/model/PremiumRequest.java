package com.gigshield.model;

import jakarta.validation.constraints.*;

public class PremiumRequest {

    @NotBlank(message = "Worker type is required")
    private String workerType; // DELIVERY, RIDE_SHARE, FREELANCE

    @NotBlank(message = "City is required")
    private String city;       // MUMBAI, DELHI, BENGALURU, CHENNAI, OTHER

    @Min(value = 1000, message = "Weekly income must be at least ₹1000")
    @Max(value = 50000, message = "Weekly income cannot exceed ₹50,000")
    private double weeklyIncome;

    @Min(value = 1, message = "Coverage weeks must be at least 1")
    @Max(value = 4, message = "Coverage weeks cannot exceed 4")
    private int coverageWeeks;

    // Getters & Setters
    public String getWorkerType() { return workerType; }
    public void setWorkerType(String workerType) { this.workerType = workerType; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public double getWeeklyIncome() { return weeklyIncome; }
    public void setWeeklyIncome(double weeklyIncome) { this.weeklyIncome = weeklyIncome; }

    public int getCoverageWeeks() { return coverageWeeks; }
    public void setCoverageWeeks(int coverageWeeks) { this.coverageWeeks = coverageWeeks; }
}