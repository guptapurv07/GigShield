package com.gigshield.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DisruptionTriggerRequest {

    @NotBlank(message = "Type is mandatory (e.g., EXTREME_HEAT, HEAVY_RAINFALL)")
    private String type;

    @NotBlank(message = "City is mandatory (e.g., Mumbai)")
    private String city;

    @NotNull(message = "Severity score is mandatory")
    private Integer severity;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }
}
