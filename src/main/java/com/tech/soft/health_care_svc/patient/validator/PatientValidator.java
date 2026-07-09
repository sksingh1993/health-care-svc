package com.tech.soft.health_care_svc.patient.validator;

import com.tech.soft.health_care_svc.common.exception.InvalidRequestException;
import com.tech.soft.health_care_svc.common.exception.ResourceNotFoundException;
import com.tech.soft.health_care_svc.patient.entity.Patient;
import com.tech.soft.health_care_svc.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatientValidator {
    private final PatientRepository patientRepository;

    public Patient validateActivePatient(Long patientId) {


        Patient patient = patientRepository
                .findById(patientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient",
                                patientId));

        if (!Boolean.TRUE.equals(patient.getActive())) {

            throw new InvalidRequestException(
                    "Patient is inactive.");
        }

        return patient;
    }
}
