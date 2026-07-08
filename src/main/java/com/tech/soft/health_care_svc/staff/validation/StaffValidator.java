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

    public void validateCreate(CreateStaffRequest request,Map<String, String> errors) {

        validateEmail(request.getEmail(),errors);
        validateMobile(request.getMobile(),errors);
    }

    public void validateUpdate(Long staffId, StaffUpdateRequest request,Map<String, String> errors) {

        Staff staff = getStaff(staffId);

        validateEmail(staff.getId(), request.getEmail(),errors);
        validateMobile(staff.getId(), request.getMobile(),errors);

        validateVersion(staff, request.getVersion(),errors);
    }

    public Staff getStaff(Long id) {
        return staffRepository.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Staff not found with id : " + id));
    }

    private void validateEmail(String email,Map<String, String> errors) {

        if (email != null && staffRepository.existsByEmail(email)) {

            errors.put("email","Email already exists. : "+ email);
            //throw new DuplicateResourceException(errors);
            //throw new DuplicateResourceException("Email already exists.");
        }
    }

    private void validateMobile(String mobile,Map<String, String> errors) {

        if (mobile != null && staffRepository.existsByMobile(mobile)) {
            errors.put("mobile","Mobile number already exists. : "+ mobile);
            //throw new DuplicateResourceException(errors);
            //throw new DuplicateResourceException("Mobile number already exists.");
        }
    }

    private void validateEmail(Long staffId, String email,Map<String, String> errors) {

        if (email == null) {
            return;
        }

        staffRepository.findByEmail(email)
                .filter(staff -> !staff.getId().equals(staffId))
                .ifPresent(staff -> {

                    errors.put("email","Email already exists. : "+ email);
                    //throw new DuplicateResourceException(errors);
                    //throw new DuplicateResourceException("Email already exists.");
                });
    }

    private void validateMobile(Long staffId, String mobile,Map<String, String> errors) {

        if (mobile == null) {
            return;
        }

        staffRepository.findByMobile(mobile)
                .filter(staff -> !staff.getId().equals(staffId))
                .ifPresent(staff -> {
                    errors.put("mobile","Mobile number already exists. : "+ mobile);
                    //throw new DuplicateResourceException(errors);
                    //throw new DuplicateResourceException("Mobile number already exists.");
                });
    }

    private void validateVersion(Staff staff, Long version,Map<String, String> errors) {

        if (!staff.getVersion().equals(version)) {
            errors.put("version","Staff record has been modified by another user. Please refresh and try again. : "
                            + version);
            //throw new DuplicateResourceException(errors);
//            throw new DuplicateResourceException(
//                    "Staff record has been modified by another user. Please refresh and try again.");
        }
    }
}