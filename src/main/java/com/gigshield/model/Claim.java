package com.gigshield.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "claims")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long policyId;

    @Column(nullable = false)
    private String disruptionType;

    @Column(nullable = false)
    private Double payoutAmount;

    @Column(nullable = false)
    private String status; // INITIATED, PAID

    @Column(nullable = false)
    private LocalDateTime triggeredAt;

    public Claim() {
    }

    public Claim(Long policyId, String disruptionType, Double payoutAmount, String status) {
        this.policyId = policyId;
        this.disruptionType = disruptionType;
        this.payoutAmount = payoutAmount;
        this.status = status;
        this.triggeredAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPolicyId() { return policyId; }
    public void setPolicyId(Long policyId) { this.policyId = policyId; }

    public String getDisruptionType() { return disruptionType; }
    public void setDisruptionType(String disruptionType) { this.disruptionType = disruptionType; }

    public Double getPayoutAmount() { return payoutAmount; }
    public void setPayoutAmount(Double payoutAmount) { this.payoutAmount = payoutAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getTriggeredAt() { return triggeredAt; }
    public void setTriggeredAt(LocalDateTime triggeredAt) { this.triggeredAt = triggeredAt; }
}
