package com.gigshield.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "policies")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long workerId;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private Double premiumPaid;

    @Column(nullable = false)
    private Double maxPayoutCoverage;

    @Column(nullable = false)
    private Boolean isActive;

    public Policy() {
    }

    public Policy(Long workerId, LocalDateTime startDate, LocalDateTime endDate, Double premiumPaid, Double maxPayoutCoverage, Boolean isActive) {
        this.workerId = workerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.premiumPaid = premiumPaid;
        this.maxPayoutCoverage = maxPayoutCoverage;
        this.isActive = isActive;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getWorkerId() { return workerId; }
    public void setWorkerId(Long workerId) { this.workerId = workerId; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public Double getPremiumPaid() { return premiumPaid; }
    public void setPremiumPaid(Double premiumPaid) { this.premiumPaid = premiumPaid; }

    public Double getMaxPayoutCoverage() { return maxPayoutCoverage; }
    public void setMaxPayoutCoverage(Double maxPayoutCoverage) { this.maxPayoutCoverage = maxPayoutCoverage; }

    public Boolean getActive() { return isActive; }
    public void setActive(Boolean active) { isActive = active; }
}
