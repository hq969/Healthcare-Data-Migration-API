package com.healthcare.mapper;

import com.healthcare.domain.*;
import com.healthcare.dto.*;

public class EntityMapper {

    public static Patient toEntity(PatientDto dto, Patient target) {
        if (target == null) target = new Patient();
        target.setFirstName(dto.getFirstName());
        target.setLastName(dto.getLastName());
        target.setDateOfBirth(dto.getDateOfBirth());
        target.setMrn(dto.getMrn());
        target.setContactNumber(dto.getContactNumber());
        target.setEmail(dto.getEmail());
        return target;
    }

    public static PatientDto toDto(Patient entity) {
        PatientDto dto = new PatientDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setMrn(entity.getMrn());
        dto.setContactNumber(entity.getContactNumber());
        dto.setEmail(entity.getEmail());
        return dto;
    }
}
