package com.tech.soft.health_care_svc.doctor.leave.dto.request;


import com.tech.soft.health_care_svc.doctor.leave.enums.LeaveType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DoctorLeaveSearchRequest {

    private Long doctorId;

    private LocalDate fromDate;

    private LocalDate toDate;

    private LeaveType leaveType;
}