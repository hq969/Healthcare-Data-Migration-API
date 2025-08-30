package com.healthcare.service;

import com.healthcare.domain.Patient;
import com.healthcare.dto.PatientDto;
import com.healthcare.exception.NotFoundException;
import com.healthcare.mapper.EntityMapper;
import com.healthcare.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> findAll() { return patientRepository.findAll(); }

    public Patient findById(Long id) {
        return patientRepository.findById(id).orElseThrow(() -> new NotFoundException("Patient not found: " + id));
    }

    @Transactional
    public Patient create(PatientDto dto) {
        if (patientRepository.existsByMrn(dto.getMrn())) {
            throw new IllegalArgumentException("MRN already exists: " + dto.getMrn());
        }
        Patient entity = EntityMapper.toEntity(dto, null);
        return patientRepository.save(entity);
    }

    @Transactional
    public Patient update(Long id, PatientDto dto) {
        Patient existing = findById(id);
        // MRN is immutable post-creation in many systems; keep/validate if needed
        dto.setMrn(existing.getMrn());
        EntityMapper.toEntity(dto, existing);
        return patientRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Patient existing = findById(id);
        patientRepository.delete(existing);
    }
}
