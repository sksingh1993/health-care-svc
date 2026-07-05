package com.tech.soft.health_care_svc.staff.service.impl;

import com.tech.soft.health_care_svc.auth.dto.CreateUserRequest;
import com.tech.soft.health_care_svc.auth.entity.User;
import com.tech.soft.health_care_svc.auth.mapper.UserMapper;
import com.tech.soft.health_care_svc.auth.service.UserManagementService;
import com.tech.soft.health_care_svc.common.exception.DuplicateResourceException;
import com.tech.soft.health_care_svc.common.exception.ResourceNotFoundException;
import com.tech.soft.health_care_svc.common.util.CodeGenerator;
import com.tech.soft.health_care_svc.doctor.enums.Specialization;
import com.tech.soft.health_care_svc.staff.dto.request.CreateStaffRequest;
import com.tech.soft.health_care_svc.staff.dto.request.StaffSearchRequest;
import com.tech.soft.health_care_svc.staff.dto.request.StaffUpdateRequest;
import com.tech.soft.health_care_svc.staff.dto.response.StaffResponse;
import com.tech.soft.health_care_svc.staff.entity.Staff;
import com.tech.soft.health_care_svc.staff.mapper.StaffMapper;
import com.tech.soft.health_care_svc.staff.repository.StaffRepository;
import com.tech.soft.health_care_svc.staff.service.StaffService;
import com.tech.soft.health_care_svc.staff.specification.StaffSpecification;
import com.tech.soft.health_care_svc.staff.specification.StaffSpecification1;
import com.tech.soft.health_care_svc.staff.validation.StaffValidator;
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
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final StaffMapper staffMapper;
    private final UserManagementService userManagementService;
    private final UserMapper userMapper;
    private final StaffValidator staffValidator;

    @Override
    public StaffResponse create(CreateStaffRequest request) {
        staffValidator.validateCreate(request);

        // 1. Create user
        CreateUserRequest userRequest = userMapper.toUserRequest(request);
        User staffUser = userManagementService.createStaffUser(userRequest);

        // 2. Map request to entity
        Staff staff = staffMapper.toEntity(request);

        // 3. Set generated user id
        staff.setUser(staffUser);

        //staff.setDesignation(staffUser.getRoles().stream().ma);
        Staff saved = staffRepository.save(staff);
        // 4. Generate employee code
        staff.setEmployeeCode(
                CodeGenerator.generate("EMP", staff.getId()));


        // 5. Save
        saved = staffRepository.save(staff);

        // 6. Return response
        StaffResponse response = staffMapper.toResponse(saved);
        response.setUserId(staffUser.getId());
        return response;
    }
    @Override
    @Transactional(readOnly = true)
    public StaffResponse getStaff(Long id) {

        Staff staff = staffRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Staff", id));

        return staffMapper.toResponse(staff);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<StaffResponse> searchStaff(
            StaffSearchRequest request,
            Pageable pageable) {
        Page<Staff> page = staffRepository.findAll(
                StaffSpecification.search(request),
                pageable);

        return page.map(staffMapper::toResponse);

        /*Specialization specializationEnum = null;
        if (request.getDepartment() != null) {
            specializationEnum = Specialization.valueOf(request.getDepartment().toUpperCase());
        }
        Specification<Staff> specification =
                Specification.where(StaffSpecification1.isActive())
                        .and(StaffSpecification1.hasEmployeeId(request.getStaffCode()))
                        .and(StaffSpecification1.hasName(request.getName()))
                        .and(StaffSpecification1.hasMobile(request.getMobile()))
                        .and(StaffSpecification1.hasSpecialization(specializationEnum));

        return staffRepository.findAll(specification, pageable)
                .map(staffMapper::toResponse);*/
    }

    @Override
    public StaffResponse updateStaff(
            Long id,
            StaffUpdateRequest request) {

        Staff staff = staffRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor", id));

        staffValidator.validateUpdate(id, request);

        staffMapper.updateEntity(request, staff);

        staff = staffRepository.save(staff);


        return staffMapper.toResponse(staff);
    }

    @Override
    public void deleteStaff(Long id) {

        Staff staff = staffRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor", id));

        staff.setActive(false);

        staffRepository.save(staff);
    }

    /*private void validateDuplicateFields(DoctorRequest request) {

        if (doctorRepository.existsByMobile(request.getMobile())) {
            throw new DuplicateResourceException("Mobile number already exists.");
        }

        if (request.getEmail() != null &&
                doctorRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists.");
        }

        if (doctorRepository.existsByRegistrationNumber(
                request.getRegistrationNumber())) {
            throw new DuplicateResourceException("Registration number already exists.");
        }
    }*/

    private void validateUpdate(
            StaffUpdateRequest request,
            Staff staff) {

        if (!staff.getMobile().equals(request.getMobile()) &&
                staffRepository.existsByMobile(request.getMobile())) {
            Map<String, String> errors = new HashMap<>();
            errors.put(
                    "mobile",
                    "Mobile number already exists. : "
                            + request.getMobile());
            throw new DuplicateResourceException(errors);
            //throw new DuplicateResourceException("Mobile number already exists.");
        }

        if (request.getEmail() != null &&
                !request.getEmail().equals(staff.getEmail()) &&
                staffRepository.existsByEmail(request.getEmail())) {
            Map<String, String> errors = new HashMap<>();
            errors.put(
                    "email",
                    "Email already exists. : "
                            + request.getEmail());
            throw new DuplicateResourceException(errors);
            //throw new DuplicateResourceException("Email already exists.");
        }

        /*if (!staff.getEmployeeCode().equals(request.getE()) &&
                doctorRepository.existsByRegistrationNumber(
                        request.getRegistrationNumber())) {

            throw new DuplicateResourceException(
                    "Registration number already exists.");
        }*/
    }
}