package com.tech.soft.health_care_svc.doctor.schedule.util;

import lombok.experimental.UtilityClass;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class SlotGeneratorUtil {

    public List<LocalTime> generateSlots(
            LocalTime startTime,
            LocalTime endTime,
            Integer slotDuration) {

        List<LocalTime> slots = new ArrayList<>();

        LocalTime current = startTime;

        while (current.plusMinutes(slotDuration)
                .compareTo(endTime) <= 0) {

            slots.add(current);

            current = current.plusMinutes(slotDuration);
        }

        return slots;
    }
}