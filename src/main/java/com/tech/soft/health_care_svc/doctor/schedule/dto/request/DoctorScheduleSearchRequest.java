package com.tech.soft.health_care_svc.doctor.schedule.dto.request;


import com.tech.soft.health_care_svc.doctor.schedule.enums.DayOfWeek;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorScheduleSearchRequest {

    private DayOfWeek dayOfWeek;

    private Integer slotDuration;
}