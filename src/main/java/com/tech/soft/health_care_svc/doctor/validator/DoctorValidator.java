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

@Component
@RequiredArgsConstructor
public class DoctorValidator {

    private final DoctorRepository doctorRepository;

    public void validateCreate(DoctorRequest request) {

        validateMobile(request.getMobile());

        validateEmail(request.getEmail());

        validateRegistrationNumber(request.getRegistrationNumber());
    }

    public void validateUpdate(
            DoctorUpdateRequest request,
            Doctor doctor) {

        if (!doctor.getMobile().equals(request.getMobile())) {
            validateMobile(request.getMobile());
        }

        if (request.getEmail() != null &&
                !request.getEmail().equalsIgnoreCase(doctor.getEmail())) {

            validateEmail(request.getEmail());
        }

        if (!doctor.getRegistrationNumber()
                .equals(request.getRegistrationNumber())) {

            validateRegistrationNumber(
                    request.getRegistrationNumber());
        }
    }

    private void validateMobile(String mobile) {

        if (doctorRepository.existsByMobile(mobile)) {
            throw new DuplicateResourceException(
                    "Doctor already exists with mobile : " + mobile);
        }
    }

    private void validateEmail(String email) {

        if (email != null &&
                doctorRepository.existsByEmail(email)) {

            throw new DuplicateResourceException(
                    "Doctor already exists with email : " + email);
        }
    }

    private void validateRegistrationNumber(
            String registrationNumber) {

        if (doctorRepository.existsByRegistrationNumber(
                registrationNumber)) {

            throw new DuplicateResourceException(
                    "Registration number already exists : "
                            + registrationNumber);
        }
    }

    public Doctor validateActiveDoctor(Long doctorId) {

        Doctor doctor = doctorRepository
                .findById(doctorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor",
                                doctorId));

        if (!Boolean.TRUE.equals(doctor.getActive())) {

            throw new InvalidRequestException(
                    "Doctor is inactive.");
        }

        return doctor;
    }
}