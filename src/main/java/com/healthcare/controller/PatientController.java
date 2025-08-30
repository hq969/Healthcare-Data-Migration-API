package com.healthcare.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.domain.Patient;
import com.healthcare.dto.PatientDto;
import com.healthcare.mapper.EntityMapper;
import com.healthcare.service.AuditService;
import com.healthcare.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    private final PatientService service;
    private final AuditService audit;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PatientController(PatientService service, AuditService audit) {
        this.service = service;
        this.audit = audit;
    }

    @GetMapping
    public List<PatientDto> all() {
        return service.findAll().stream().map(EntityMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PatientDto byId(@PathVariable Long id) {
        return EntityMapper.toDto(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDto create(@Valid @RequestBody PatientDto dto) throws JsonProcessingException {
        Patient p = service.create(dto);
        audit.record("PATIENT", String.valueOf(p.getId()), "CREATED", objectMapper.writeValueAsString(dto));
        return EntityMapper.toDto(p);
    }

    @PutMapping("/{id}")
    public PatientDto update(@PathVariable Long id, @Valid @RequestBody PatientDto dto) throws JsonProcessingException {
        Patient p = service.update(id, dto);
        audit.record("PATIENT", String.valueOf(p.getId()), "UPDATED", objectMapper.writeValueAsString(dto));
        return EntityMapper.toDto(p);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
        audit.record("PATIENT", String.valueOf(id), "DELETED", "{}");
    }
}
