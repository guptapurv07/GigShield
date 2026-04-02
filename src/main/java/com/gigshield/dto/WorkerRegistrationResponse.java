package com.gigshield.dto;

import java.time.LocalDateTime;

/**
 * Response returned to the client after successful worker registration.
 * Never exposes sensitive data like passwordHash or full Aadhaar number.
 */
public class WorkerRegistrationResponse {

    private Long workerId;
    private String fullName;
    private String email;
    private String phone;
    private String platform;
    private String city;
    private String vehicleType;
    private Double averageWeeklyEarnings;
    private Double estimatedWeeklyPremium;   // ₹ — calculated at registration time
    private String status;
    private LocalDateTime registeredAt;
    private String message;

    // ── Constructor ───────────────────────────────────────────────
    public WorkerRegistrationResponse() {}

    public WorkerRegistrationResponse(Long workerId, String fullName, String email,
                                      String phone, String platform, String city,
                                      String vehicleType, Double averageWeeklyEarnings,
                                      Double estimatedWeeklyPremium, String status,
                                      LocalDateTime registeredAt, String message) {
        this.workerId = workerId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.platform = platform;
        this.city = city;
        this.vehicleType = vehicleType;
        this.averageWeeklyEarnings = averageWeeklyEarnings;
        this.estimatedWeeklyPremium = estimatedWeeklyPremium;
        this.status = status;
        this.registeredAt = registeredAt;
        this.message = message;
    }

    // ── Getters & Setters ─────────────────────────────────────────
    public Long getWorkerId() { return workerId; }
    public void setWorkerId(Long workerId) { this.workerId = workerId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public Double getAverageWeeklyEarnings() { return averageWeeklyEarnings; }
    public void setAverageWeeklyEarnings(Double averageWeeklyEarnings) {
        this.averageWeeklyEarnings = averageWeeklyEarnings;
    }

    public Double getEstimatedWeeklyPremium() { return estimatedWeeklyPremium; }
    public void setEstimatedWeeklyPremium(Double estimatedWeeklyPremium) {
        this.estimatedWeeklyPremium = estimatedWeeklyPremium;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(LocalDateTime registeredAt) { this.registeredAt = registeredAt; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}