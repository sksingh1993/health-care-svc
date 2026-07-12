package com.tech.soft.health_care_svc.doctor.leave.validator;


import com.tech.soft.health_care_svc.doctor.entity.Doctor;
import com.tech.soft.health_care_svc.doctor.leave.dto.request.DoctorLeaveRequest;
import com.tech.soft.health_care_svc.doctor.leave.dto.request.DoctorLeaveUpdateRequest;
import com.tech.soft.health_care_svc.doctor.leave.entity.DoctorLeave;

import java.time.LocalDate;
import java.util.Map;

public interface DoctorLeaveValidator {

    Doctor validateCreate(Long doctorId, DoctorLeaveRequest request, Map<String, String> errors);

    Doctor validateUpdate(Long doctorId,Long id,
                          DoctorLeaveUpdateRequest request,Map<String, String> errors);

    DoctorLeave validateLeave(Long doctorId,Long id);

    void validateDoctorAvailability(
            Long doctorId,
            LocalDate fromDate,
            LocalDate toDate);
}