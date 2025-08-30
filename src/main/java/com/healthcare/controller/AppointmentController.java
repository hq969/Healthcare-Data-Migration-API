package com.healthcare.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.domain.Appointment;
import com.healthcare.dto.AppointmentDto;
import com.healthcare.service.AppointmentService;
import com.healthcare.service.AuditService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    private final AppointmentService service;
    private final AuditService audit;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AppointmentController(AppointmentService service, AuditService audit) {
        this.service = service;
        this.audit = audit;
    }

    @GetMapping
    public List<AppointmentDto> all() {
        return service.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AppointmentDto byId(@PathVariable Long id) {
        return toDto(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentDto create(@Valid @RequestBody AppointmentDto dto) throws JsonProcessingException {
        Appointment a = service.create(dto);
        audit.record("APPOINTMENT", String.valueOf(a.getId()), "CREATED", objectMapper.writeValueAsString(dto));
        return toDto(a);
    }

    @PutMapping("/{id}")
    public AppointmentDto update(@PathVariable Long id, @Valid @RequestBody AppointmentDto dto) throws JsonProcessingException {
        Appointment a = service.update(id, dto);
        audit.record("APPOINTMENT", String.valueOf(a.getId()), "UPDATED", objectMapper.writeValueAsString(dto));
        return toDto(a);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
        audit.record("APPOINTMENT", String.valueOf(id), "DELETED", "{}");
    }

    private AppointmentDto toDto(Appointment a) {
        AppointmentDto dto = new AppointmentDto();
        dto.setId(a.getId());
        dto.setPatientId(a.getPatient().getId());
        dto.setScheduledAt(a.getScheduledAt());
        dto.setDepartment(a.getDepartment());
        dto.setDoctor(a.getDoctor());
        dto.setStatus(a.getStatus());
        return dto;
    }
}
