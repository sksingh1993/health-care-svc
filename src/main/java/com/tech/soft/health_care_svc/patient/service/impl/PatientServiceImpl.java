package com.tech.soft.health_care_svc.patient.service.impl;

import com.tech.soft.health_care_svc.common.util.CodeGenerator;
import com.tech.soft.health_care_svc.common.util.PatientCodeGenerator;
import com.tech.soft.health_care_svc.patient.dto.request.PatientRequest;
import com.tech.soft.health_care_svc.patient.dto.request.PatientUpdateRequest;
import com.tech.soft.health_care_svc.patient.dto.response.PatientResponse;
import com.tech.soft.health_care_svc.patient.dto.search.PatientSearchRequest;
import com.tech.soft.health_care_svc.patient.entity.Patient;
import com.tech.soft.health_care_svc.patient.exception.DuplicateMobileException;
import com.tech.soft.health_care_svc.patient.exception.PatientNotFoundException;
import com.tech.soft.health_care_svc.patient.mapper.PatientMapper;
import com.tech.soft.health_care_svc.patient.repository.PatientRepository;
import com.tech.soft.health_care_svc.patient.repository.PatientSpecification;
import com.tech.soft.health_care_svc.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    //AuditorAware<Long> auditorAware;

    @Override
    public PatientResponse createPatient(PatientRequest request) {
       // System.out.println(auditorAware.getCurrentAuditor().get());

        //return new PatientResponse();
        if (patientRepository.existsByMobile(request.getMobile())) {
            throw new DuplicateMobileException("Mobile number already exists.");
        }

        Patient patient = patientMapper.toEntity(request);

        patient = patientRepository.save(patient);

        patient.setPatientCode(
                CodeGenerator.generate("PAT",patient.getId()));

        patient = patientRepository.save(patient);

        return patientMapper.toResponse(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponse getPatient(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new PatientNotFoundException("Patient not found"));

        return patientMapper.toResponse(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PatientResponse> searchPatients(
            PatientSearchRequest request,
            Pageable pageable) {

        Specification<Patient> specification =
                Specification.where(PatientSpecification.isActive())
                        .and(PatientSpecification.hasName(request.getName()))
                        .and(PatientSpecification.hasMobile(request.getMobile()))
                        .and(PatientSpecification.hasPatientCode(request.getPatientCode()));

        return patientRepository.findAll(specification, pageable)
                .map(patientMapper::toResponse);
    }

    @Override
    public PatientResponse updatePatient(
            Long id,
            PatientUpdateRequest request) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new PatientNotFoundException("Patient not found"));

        if (!patient.getMobile().equals(request.getMobile())
                && patientRepository.existsByMobile(request.getMobile())) {

            throw new DuplicateMobileException("Mobile number already exists.");
        }

        patientMapper.updateEntity(request, patient);

        patient = patientRepository.save(patient);

        return patientMapper.toResponse(patient);
    }

    @Override
    public void deletePatient(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new PatientNotFoundException("Patient not found"));

        patient.setActive(false);

        patientRepository.save(patient);
    }
}