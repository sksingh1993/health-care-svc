package com.tech.soft.health_care_svc.staff.service.impl;

import com.tech.soft.health_care_svc.auth.dto.CreateUserRequest;
import com.tech.soft.health_care_svc.auth.entity.User;
import com.tech.soft.health_care_svc.auth.mapper.UserMapper;
import com.tech.soft.health_care_svc.auth.service.UserManagementService;
import com.tech.soft.health_care_svc.auth.validator.UserValidator;
import com.tech.soft.health_care_svc.common.exception.DuplicateResourceException;
import com.tech.soft.health_care_svc.common.exception.ResourceNotFoundException;
import com.tech.soft.health_care_svc.common.util.CodeGenerator;
import com.tech.soft.health_care_svc.staff.enums.Department;
import com.tech.soft.health_care_svc.staff.dto.request.CreateStaffRequest;
import com.tech.soft.health_care_svc.staff.dto.request.StaffSearchRequest;
import com.tech.soft.health_care_svc.staff.dto.request.StaffUpdateRequest;
import com.tech.soft.health_care_svc.staff.dto.response.StaffResponse;
import com.tech.soft.health_care_svc.staff.entity.Staff;
import com.tech.soft.health_care_svc.staff.mapper.StaffMapper;
import com.tech.soft.health_care_svc.staff.repository.StaffRepository;
import com.tech.soft.health_care_svc.staff.service.StaffService;
import com.tech.soft.health_care_svc.staff.specification.StaffSpecification;
import com.tech.soft.health_care_svc.staff.specification.StaffSpecification2;
import com.tech.soft.health_care_svc.staff.validation.StaffValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final StaffMapper staffMapper;
    private final UserManagementService userManagementService;
    private final UserMapper userMapper;
    private final StaffValidator staffValidator;
    private final UserValidator userValidator;

    @Override
    public StaffResponse create(CreateStaffRequest request) {
        Map<String, String> errors = new HashMap<>();
        CreateUserRequest userRequest = userMapper.toUserRequest(request);
        userRequest.setRoles(Set.of(request.getDesignation().name()));
        userValidator.validateCreate(userRequest,errors);
        staffValidator.validateCreate(request,errors);

        if(!errors.isEmpty()){
            throw new DuplicateResourceException(errors);
        }

        // 1. Create user

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
        /*Page<Staff> page = staffRepository.findAll(
                StaffSpecification2.search(request),
                pageable);

        return page.map(staffMapper::toResponse);*/


        Specification<Staff> specification =
                Specification.where(StaffSpecification.isActive())
                        .and(StaffSpecification.hasEmployeeId(request.getEmployeeCode()))
                        .and(StaffSpecification.hasName(request.getFirstName()))
                        .and(StaffSpecification.hasName(request.getLastName()))
                        .and(StaffSpecification.hasMobile(request.getMobile()))
                        .and(StaffSpecification.hasDepartment(request.getDepartment()));

        return staffRepository.findAll(specification, pageable)
                .map(staffMapper::toResponse);
    }

    @Override
    public StaffResponse updateStaff(Long id, StaffUpdateRequest request) {

        Map<String, String> errors = new HashMap<>();

        Staff staff = staffRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor", id));

        staffValidator.validateUpdate(id, request,errors);

        if(!errors.isEmpty()){
            throw new DuplicateResourceException(errors);
        }

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

}