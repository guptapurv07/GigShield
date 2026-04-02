package com.gigshield.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents a Gig Worker registered on the GigShield platform.
 * Stores personal, professional, and account details.
 */
@Entity
@Table(name = "workers")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ── Personal Details ──────────────────────────────────────────
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false, unique = true, length = 10)
    private String phone;

    @Column(nullable = false, length = 12, unique = true)
    private String aadhaarNumber;  // Indian national ID

    // ── Professional Details ──────────────────────────────────────
    @Column(nullable = false)
    private String platform;       // e.g. Swiggy, Zomato, Dunzo, Blinkit

    @Column(nullable = false)
    private String city;           // e.g. Delhi, Mumbai — used for weather triggers

    @Column(nullable = false)
    private String vehicleType;    // BIKE / CYCLE / FOOT

    @Column(nullable = false)
    private Double averageWeeklyEarnings;  // Used for premium & payout calculation

    // ── Account Status ────────────────────────────────────────────
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkerStatus status;   // PENDING_VERIFICATION / ACTIVE / SUSPENDED

    @Column(nullable = false)
    private LocalDateTime registeredAt;

    // ── Constructors ──────────────────────────────────────────────
    public Worker() {}

    public Worker(String fullName, String email, String passwordHash, String phone,
                  String aadhaarNumber, String platform, String city,
                  String vehicleType, Double averageWeeklyEarnings) {
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phone = phone;
        this.aadhaarNumber = aadhaarNumber;
        this.platform = platform;
        this.city = city;
        this.vehicleType = vehicleType;
        this.averageWeeklyEarnings = averageWeeklyEarnings;
        this.status = WorkerStatus.PENDING_VERIFICATION;
        this.registeredAt = LocalDateTime.now();
    }

    // ── Getters & Setters ─────────────────────────────────────────
    public Long getId() { return id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

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

    public WorkerStatus getStatus() { return status; }
    public void setStatus(WorkerStatus status) { this.status = status; }

    public LocalDateTime getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(LocalDateTime registeredAt) { this.registeredAt = registeredAt; }

    // ── Status Enum ───────────────────────────────────────────────
    public enum WorkerStatus {
        PENDING_VERIFICATION,
        ACTIVE,
        SUSPENDED
    }
}