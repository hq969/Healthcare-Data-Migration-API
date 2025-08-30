package com.healthcare.dto;

import com.healthcare.domain.Billing;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BillingDto {
    private Long id;

    @NotNull
    private Long patientId;

    @NotNull @DecimalMin(value = "0.00")
    private BigDecimal amount;

    @NotNull
    private LocalDateTime billedAt;

    private Billing.Status status = Billing.Status.PENDING;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public LocalDateTime getBilledAt() { return billedAt; }
    public void setBilledAt(LocalDateTime billedAt) { this.billedAt = billedAt; }
    public Billing.Status getStatus() { return status; }
    public void setStatus(Billing.Status status) { this.status = status; }
}
