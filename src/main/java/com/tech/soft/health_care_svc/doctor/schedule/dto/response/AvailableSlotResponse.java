package com.tech.soft.health_care_svc.doctor.schedule.dto.response;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableSlotResponse {

    private LocalTime startTime;

    private LocalTime endTime;

    private boolean available;

    private Integer bookedCount;

    private Integer remainingCapacity;
}