package com.tech.soft.health_care_svc.doctor.schedule.dto.request;


import com.tech.soft.health_care_svc.doctor.schedule.enums.DayOfWeek;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class DoctorScheduleRequest {

    @NotNull(message = "Day of week is required")
    private DayOfWeek dayOfWeek;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @NotNull(message = "Slot duration is required")
    @Min(value = 5, message = "Slot duration must be at least 5 minutes")
    private Integer slotDuration;

    @NotNull(message = "Consultation limit is required")
    @Min(value = 1, message = "Consultation limit must be at least 1")
    private Integer consultationLimit;
}