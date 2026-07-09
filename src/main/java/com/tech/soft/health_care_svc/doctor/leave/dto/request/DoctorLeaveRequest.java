package com.tech.soft.health_care_svc.doctor.leave.dto.request;


import com.tech.soft.health_care_svc.doctor.leave.enums.LeaveType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorLeaveRequest {

    @NotNull
    @FutureOrPresent
    private LocalDate fromDate;

    @NotNull
    @FutureOrPresent
    private LocalDate toDate;

    @NotNull
    private LeaveType leaveType;

    @Size(max = 500)
    private String reason;
}