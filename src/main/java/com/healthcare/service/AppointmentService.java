package com.healthcare.service;

import com.healthcare.domain.Appointment;
import com.healthcare.domain.Patient;
import com.healthcare.dto.AppointmentDto;
import com.healthcare.exception.NotFoundException;
import com.healthcare.repository.AppointmentRepository;
import com.healthcare.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository repo;
    private final PatientRepository patientRepo;

    public AppointmentService(AppointmentRepository repo, PatientRepository patientRepo) {
        this.repo = repo;
        this.patientRepo = patientRepo;
    }

    public List<Appointment> findAll() { return repo.findAll(); }

    public Appointment findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Appointment not found: " + id));
    }

    @Transactional
    public Appointment create(AppointmentDto dto) {
        Patient patient = patientRepo.findById(dto.getPatientId())
                .orElseThrow(() -> new NotFoundException("Patient not found: " + dto.getPatientId()));
        Appointment a = new Appointment();
        a.setPatient(patient);
        a.setScheduledAt(dto.getScheduledAt());
        a.setDepartment(dto.getDepartment());
        a.setDoctor(dto.getDoctor());
        a.setStatus(dto.getStatus());
        return repo.save(a);
    }

    @Transactional
    public Appointment update(Long id, AppointmentDto dto) {
        Appointment a = findById(id);
        if (!a.getPatient().getId().equals(dto.getPatientId())) {
            Patient patient = patientRepo.findById(dto.getPatientId())
                    .orElseThrow(() -> new NotFoundException("Patient not found: " + dto.getPatientId()));
            a.setPatient(patient);
        }
        a.setScheduledAt(dto.getScheduledAt());
        a.setDepartment(dto.getDepartment());
        a.setDoctor(dto.getDoctor());
        a.setStatus(dto.getStatus());
        return repo.save(a);
    }

    @Transactional
    public void delete(Long id) {
        repo.delete(findById(id));
    }
}
