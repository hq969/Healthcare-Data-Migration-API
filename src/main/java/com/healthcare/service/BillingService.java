package com.healthcare.service;

import com.healthcare.domain.Billing;
import com.healthcare.domain.Patient;
import com.healthcare.dto.BillingDto;
import com.healthcare.exception.NotFoundException;
import com.healthcare.repository.BillingRepository;
import com.healthcare.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BillingService {

    private final BillingRepository repo;
    private final PatientRepository patientRepo;

    public BillingService(BillingRepository repo, PatientRepository patientRepo) {
        this.repo = repo;
        this.patientRepo = patientRepo;
    }

    public List<Billing> findAll() { return repo.findAll(); }

    public Billing findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Billing not found: " + id));
    }

    @Transactional
    public Billing create(BillingDto dto) {
        Patient patient = patientRepo.findById(dto.getPatientId())
                .orElseThrow(() -> new NotFoundException("Patient not found: " + dto.getPatientId()));
        Billing b = new Billing();
        b.setPatient(patient);
        b.setAmount(dto.getAmount());
        b.setBilledAt(dto.getBilledAt());
        b.setStatus(dto.getStatus());
        return repo.save(b);
    }

    @Transactional
    public Billing update(Long id, BillingDto dto) {
        Billing b = findById(id);
        if (!b.getPatient().getId().equals(dto.getPatientId())) {
            Patient patient = patientRepo.findById(dto.getPatientId())
                    .orElseThrow(() -> new NotFoundException("Patient not found: " + dto.getPatientId()));
            b.setPatient(patient);
        }
        b.setAmount(dto.getAmount());
        b.setBilledAt(dto.getBilledAt());
        b.setStatus(dto.getStatus());
        return repo.save(b);
    }

    @Transactional
    public void delete(Long id) {
        repo.delete(findById(id));
    }
}
