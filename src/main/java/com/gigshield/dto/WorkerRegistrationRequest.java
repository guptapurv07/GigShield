package com.gigshield.dto;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object for Worker Registration.
 * All fields are validated before processing.
 */
public class WorkerRegistrationRequest {

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,}$",
        message = "Password must contain at least one uppercase letter, one number, and one special character"
    )
    private String password;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Please provide a valid 10-digit Indian mobile number")
    private String phone;

    @NotBlank(message = "Aadhaar number is required")
    @Pattern(regexp = "^\\d{12}$", message = "Aadhaar number must be exactly 12 digits")
    private String aadhaarNumber;

    @NotBlank(message = "Platform is required (e.g. Swiggy, Zomato, Dunzo, Blinkit)")
    private String platform;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Vehicle type is required")
    @Pattern(regexp = "^(BIKE|CYCLE|FOOT)$", message = "Vehicle type must be BIKE, CYCLE, or FOOT")
    private String vehicleType;

    @NotNull(message = "Average weekly earnings are required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Weekly earnings must be greater than 0")
    @DecimalMax(value = "50000.0", message = "Weekly earnings cannot exceed ₹50,000")
    private Double averageWeeklyEarnings;

    // ── Getters & Setters ─────────────────────────────────────────
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAadhaarNumber() { return aadhaarNumber; }
    public void setAadhaarNumber(String aadhaarNumber) { this.aadhaarNumber = aadhaarNumber; }

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
}