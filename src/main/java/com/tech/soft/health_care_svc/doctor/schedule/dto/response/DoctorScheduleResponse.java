package com.tech.soft.health_care_svc.doctor.schedule.dto.response;


import com.tech.soft.health_care_svc.doctor.schedule.enums.DayOfWeek;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class DoctorScheduleResponse {

    private Long id;

    private Long doctorId;

    private String doctorCode;

    private String doctorName;

    private DayOfWeek dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer slotDuration;

    private Integer consultationLimit;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}