package com.tech.soft.health_care_svc.doctor.leave.dto.response;


import com.tech.soft.health_care_svc.doctor.leave.enums.LeaveStatus;
import com.tech.soft.health_care_svc.doctor.leave.enums.LeaveType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorLeaveResponse {

    private Long id;

    private Long doctorId;

    private String doctorCode;

    private String doctorName;

    private LocalDate fromDate;

    private LocalDate toDate;

    private Integer numberOfDays;

    private LeaveType leaveType;

    private String reason;

    private LeaveStatus status;
}