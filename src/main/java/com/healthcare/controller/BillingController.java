package com.healthcare.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.domain.Billing;
import com.healthcare.dto.BillingDto;
import com.healthcare.service.AuditService;
import com.healthcare.service.BillingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/billings")
public class BillingController {

    private final BillingService service;
    private final AuditService audit;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public BillingController(BillingService service, AuditService audit) {
        this.service = service;
        this.audit = audit;
    }

    @GetMapping
    public List<BillingDto> all() {
        return service.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BillingDto byId(@PathVariable Long id) {
        return toDto(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BillingDto create(@Valid @RequestBody BillingDto dto) throws JsonProcessingException {
        Billing b = service.create(dto);
        audit.record("BILLING", String.valueOf(b.getId()), "CREATED", objectMapper.writeValueAsString(dto));
        return toDto(b);
    }

    @PutMapping("/{id}")
    public BillingDto update(@PathVariable Long id, @Valid @RequestBody BillingDto dto) throws JsonProcessingException {
        Billing b = service.update(id, dto);
        audit.record("BILLING", String.valueOf(b.getId()), "UPDATED", objectMapper.writeValueAsString(dto));
        return toDto(b);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
        audit.record("BILLING", String.valueOf(id), "DELETED", "{}");
    }

    private BillingDto toDto(Billing b) {
        BillingDto dto = new BillingDto();
        dto.setId(b.getId());
        dto.setPatientId(b.getPatient().getId());
        dto.setAmount(b.getAmount());
        dto.setBilledAt(b.getBilledAt());
        dto.setStatus(b.getStatus());
        return dto;
    }
}
