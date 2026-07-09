package com.tech.soft.health_care_svc.appointment.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder
public class AvailableSlotResponse {

    private LocalTime startTime;

    private LocalTime endTime;

    private boolean available;

    private Integer remainingCapacity;

    private Integer bookedCount;
}