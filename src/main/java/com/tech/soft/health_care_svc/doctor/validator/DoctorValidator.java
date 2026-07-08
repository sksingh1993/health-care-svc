package com.tech.soft.health_care_svc.doctor.validator;

import com.tech.soft.health_care_svc.common.exception.DuplicateResourceException;
import com.tech.soft.health_care_svc.common.exception.InvalidRequestException;
import com.tech.soft.health_care_svc.common.exception.ResourceNotFoundException;
import com.tech.soft.health_care_svc.doctor.dto.request.DoctorRequest;
import com.tech.soft.health_care_svc.doctor.dto.request.DoctorUpdateRequest;

import com.tech.soft.health_care_svc.doctor.repository.DoctorRepository;
import com.tech.soft.health_care_svc.doctor.entity.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DoctorValidator {

    private final DoctorRepository doctorRepository;

    public void validateCreate(DoctorRequest request,Map<String, String> errors) {
        validateMobile(request.getMobile(),errors);
        validateEmail(request.getEmail(),errors);
        validateRegistrationNumber(request.getRegistrationNumber(),errors);
    }

    public void validateUpdate(DoctorUpdateRequest request, Doctor doctor,Map<String, String> errors) {

        if (!doctor.getMobile().equals(request.getMobile())) {
            validateMobile(request.getMobile(),errors);
        }

        if (request.getEmail() != null && !request.getEmail().equalsIgnoreCase(doctor.getEmail())) {
            validateEmail(request.getEmail(),errors);
        }

        if (!doctor.getRegistrationNumber().equals(request.getRegistrationNumber())) {
            validateRegistrationNumber(request.getRegistrationNumber(),errors);
        }
    }

    private void validateMobile(String mobile,Map<String, String> errors) {

        if (doctorRepository.existsByMobile(mobile)) {
            errors.put(
                    "mobile",
                    "Doctor already exists with mobile : " + mobile);
        }
    }

    private void validateEmail(String email,Map<String, String> errors) {

        if (email != null && doctorRepository.existsByEmail(email)) {
            errors.put(
                    "email",
                    "Doctor already exists with email : " + email);
        }
    }

    private void validateRegistrationNumber(String registrationNumber,Map<String, String> errors) {

        if (doctorRepository.existsByRegistrationNumber(registrationNumber)) {

            errors.put(
                    "registrationNumber",
                    "Doctor already exists with registration : " + registrationNumber);
            //throw new DuplicateResourceException("Registration number already exists : " + registrationNumber);
        }
    }

    public Doctor validateActiveDoctor(Long doctorId) {

        Doctor doctor = doctorRepository
                .findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", doctorId));

        if (!Boolean.TRUE.equals(doctor.getActive())) {
            throw new InvalidRequestException("Doctor is inactive.");
        }
        return doctor;
    }
}