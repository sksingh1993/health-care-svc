package com.tech.soft.health_care_svc.staff.validation;


import com.tech.soft.health_care_svc.common.exception.DuplicateResourceException;
import com.tech.soft.health_care_svc.staff.dto.request.CreateStaffRequest;
import com.tech.soft.health_care_svc.staff.dto.request.StaffUpdateRequest;
import com.tech.soft.health_care_svc.staff.entity.Staff;
import com.tech.soft.health_care_svc.staff.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class StaffValidator {

    private final StaffRepository staffRepository;

    public void validateCreate(CreateStaffRequest request) {

        validateEmail(request.getEmail());
        validateMobile(request.getMobile());
    }

    public void validateUpdate(Long staffId, StaffUpdateRequest request) {

        Staff staff = getStaff(staffId);

        validateEmail(staff.getId(), request.getEmail());
        validateMobile(staff.getId(), request.getMobile());

        validateVersion(staff, request.getVersion());
    }

    public Staff getStaff(Long id) {
        return staffRepository.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Staff not found with id : " + id));
    }

    private void validateEmail(String email) {

        if (email != null && staffRepository.existsByEmail(email)) {
            Map<String, String> errors = new HashMap<>();
            errors.put(
                    "email",
                    "Email already exists. : "
                            + email);
            throw new DuplicateResourceException(errors);
            //throw new DuplicateResourceException("Email already exists.");
        }
    }

    private void validateMobile(String mobile) {

        if (mobile != null && staffRepository.existsByMobile(mobile)) {
            Map<String, String> errors = new HashMap<>();
            errors.put(
                    "mobile",
                    "Mobile number already exists. : "
                            + mobile);
            throw new DuplicateResourceException(errors);
            //throw new DuplicateResourceException("Mobile number already exists.");
        }
    }

    private void validateEmail(Long staffId, String email) {

        if (email == null) {
            return;
        }

        staffRepository.findByEmail(email)
                .filter(staff -> !staff.getId().equals(staffId))
                .ifPresent(staff -> {
                    Map<String, String> errors = new HashMap<>();
                    errors.put(
                            "email",
                            "Email already exists. : "
                                    + email);
                    throw new DuplicateResourceException(errors);
                    //throw new DuplicateResourceException("Email already exists.");
                });
    }

    private void validateMobile(Long staffId, String mobile) {

        if (mobile == null) {
            return;
        }

        staffRepository.findByMobile(mobile)
                .filter(staff -> !staff.getId().equals(staffId))
                .ifPresent(staff -> {
                    Map<String, String> errors = new HashMap<>();
                    errors.put(
                            "mobile",
                            "Mobile number already exists. : "
                                    + mobile);
                    throw new DuplicateResourceException(errors);
                    //throw new DuplicateResourceException("Mobile number already exists.");
                });
    }

    private void validateVersion(Staff staff, Long version) {

        if (!staff.getVersion().equals(version)) {
            Map<String, String> errors = new HashMap<>();
            errors.put(
                    "version",
                    "Staff record has been modified by another user. Please refresh and try again. : "
                            + version);
            throw new DuplicateResourceException(errors);
//            throw new DuplicateResourceException(
//                    "Staff record has been modified by another user. Please refresh and try again.");
        }
    }
}