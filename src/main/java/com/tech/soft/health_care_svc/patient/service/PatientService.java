package com.tech.soft.health_care_svc.patient.service;

import com.tech.soft.health_care_svc.patient.dto.request.PatientRequest;
import com.tech.soft.health_care_svc.patient.dto.request.PatientUpdateRequest;
import com.tech.soft.health_care_svc.patient.dto.response.PatientResponse;
import com.tech.soft.health_care_svc.patient.dto.search.PatientSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PatientService {

    PatientResponse createPatient(PatientRequest request);

    PatientResponse getPatient(Long id);

    Page<PatientResponse> searchPatients(
            PatientSearchRequest request,
            Pageable pageable);

    PatientResponse updatePatient(
            Long id,
            PatientUpdateRequest request);

    void deletePatient(Long id);

}