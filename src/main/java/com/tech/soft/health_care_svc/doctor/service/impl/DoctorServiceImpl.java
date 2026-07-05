package com.tech.soft.health_care_svc.doctor.service.impl;

import com.tech.soft.health_care_svc.auth.dto.CreateUserRequest;
import com.tech.soft.health_care_svc.auth.dto.UserResponse;
import com.tech.soft.health_care_svc.auth.entity.User;
import com.tech.soft.health_care_svc.auth.mapper.UserMapper;
import com.tech.soft.health_care_svc.auth.service.UserManagementService;
import com.tech.soft.health_care_svc.common.exception.DuplicateResourceException;
import com.tech.soft.health_care_svc.common.exception.ResourceNotFoundException;
import com.tech.soft.health_care_svc.common.util.CodeGenerator;
import com.tech.soft.health_care_svc.common.util.DoctorCodeGenerator;
import com.tech.soft.health_care_svc.doctor.dto.request.DoctorRequest;
import com.tech.soft.health_care_svc.doctor.dto.request.DoctorSearchRequest;
import com.tech.soft.health_care_svc.doctor.dto.request.DoctorUpdateRequest;
import com.tech.soft.health_care_svc.doctor.dto.response.DoctorResponse;

import com.tech.soft.health_care_svc.doctor.enums.Specialization;
import com.tech.soft.health_care_svc.doctor.mapper.DoctorMapper;
import com.tech.soft.health_care_svc.doctor.repository.DoctorRepository;
import com.tech.soft.health_care_svc.doctor.service.DoctorService;
import com.tech.soft.health_care_svc.doctor.specification.DoctorSpecification;
import com.tech.soft.health_care_svc.doctor.entity.Doctor;
import com.tech.soft.health_care_svc.doctor.validator.DoctorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final DoctorValidator doctorValidator;
    private final UserManagementService userManagementService;
    private final UserMapper userMapper;

    @Override
    public DoctorResponse createDoctor(DoctorRequest request) {

        doctorValidator.validateCreate(request);

        CreateUserRequest userRequest = userMapper.toUserRequest(request);
        User doctorUser = userManagementService.createDoctorUser(userRequest);
        Doctor doctor = doctorMapper.toEntity(request);
        doctor.setUser(doctorUser);

        doctor = doctorRepository.save(doctor);
        doctor.setDoctorCode(
                CodeGenerator.generate("DOC",doctor.getId()));

        doctor = doctorRepository.save(doctor);

        return doctorMapper.toResponse(doctor);
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorResponse getDoctor(Long id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor", id));

        return doctorMapper.toResponse(doctor);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorResponse> searchDoctors(
            DoctorSearchRequest request,
            Pageable pageable) {
        Specialization specializationEnum = null;
        if (request.getSpecialization() != null) {
            specializationEnum = Specialization.valueOf(request.getSpecialization().toUpperCase());
        }
        Specification<Doctor> specification =
                Specification.where(DoctorSpecification.isActive())
                        .and(DoctorSpecification.hasDoctorCode(request.getDoctorCode()))
                        .and(DoctorSpecification.hasName(request.getName()))
                        .and(DoctorSpecification.hasMobile(request.getMobile()))
                        .and(DoctorSpecification.hasSpecialization(specializationEnum));

        return doctorRepository.findAll(specification, pageable)
                .map(doctorMapper::toResponse);
    }

    @Override
    public DoctorResponse updateDoctor(
            Long id,
            DoctorUpdateRequest request) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor", id));

        doctorValidator.validateUpdate(request, doctor);

        doctorMapper.updateEntity(request, doctor);

        doctor = doctorRepository.save(doctor);

        return doctorMapper.toResponse(doctor);
    }

    @Override
    public void deleteDoctor(Long id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor", id));

        doctor.setActive(false);
        try{
            doctorRepository.save(doctor);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void validateDuplicateFields(DoctorRequest request) {

        if (doctorRepository.existsByMobile(request.getMobile())) {
            Map<String, String> errors = new HashMap<>();
            errors.put(
                    "mobile",
                    "Mobile number already exists. : "
                            + request.getMobile());
            throw new DuplicateResourceException(errors);
            //throw new DuplicateResourceException("Mobile number already exists.");
        }

        if (request.getEmail() != null &&
                doctorRepository.existsByEmail(request.getEmail())) {
            Map<String, String> errors = new HashMap<>();
            errors.put(
                    "email",
                    "Email already exists. : "
                            + request.getEmail());
            throw new DuplicateResourceException(errors);
            //throw new DuplicateResourceException("Email already exists.");
        }

        if (doctorRepository.existsByRegistrationNumber(
                request.getRegistrationNumber())) {
            Map<String, String> errors = new HashMap<>();
            errors.put(
                    "registration",
                    "Registration number already exists. : "
                            + request.getRegistrationNumber());
            throw new DuplicateResourceException(errors);
            //throw new DuplicateResourceException("Registration number already exists.");
        }
    }

    private void validateUpdate(
            DoctorUpdateRequest request,
            Doctor doctor) {

        if (!doctor.getMobile().equals(request.getMobile()) &&
                doctorRepository.existsByMobile(request.getMobile())) {
            Map<String, String> errors = new HashMap<>();
            errors.put(
                    "mobile",
                    "Mobile number already exists. : "
                            + request.getMobile());
            throw new DuplicateResourceException(errors);
            //throw new DuplicateResourceException("Mobile number already exists.");
        }

        if (request.getEmail() != null &&
                !request.getEmail().equals(doctor.getEmail()) &&
                doctorRepository.existsByEmail(request.getEmail())) {
            Map<String, String> errors = new HashMap<>();
            errors.put(
                    "email",
                    "Email already exists.: "
                            + doctor.getEmail());
            throw new DuplicateResourceException(errors);
            //throw new DuplicateResourceException("Email already exists.");
        }

        if (!doctor.getRegistrationNumber().equals(request.getRegistrationNumber()) &&
                doctorRepository.existsByRegistrationNumber(
                        request.getRegistrationNumber())) {
            Map<String, String> errors = new HashMap<>();
            errors.put(
                    "registration",
                    "Registration number already exists. : "
                            + request.getRegistrationNumber());
            throw new DuplicateResourceException(errors);
//            throw new DuplicateResourceException(
//                    "Registration number already exists.");
        }
    }
}