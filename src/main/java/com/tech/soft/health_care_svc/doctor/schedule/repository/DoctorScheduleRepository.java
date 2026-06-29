package com.tech.soft.health_care_svc.doctor.schedule.repository;


import com.tech.soft.health_care_svc.doctor.schedule.entity.DoctorSchedule;
import com.tech.soft.health_care_svc.doctor.schedule.enums.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface DoctorScheduleRepository extends
        JpaRepository<DoctorSchedule, Long>,
        JpaSpecificationExecutor<DoctorSchedule> {

    Optional<DoctorSchedule> findByIdAndActiveTrue(Long id);

    List<DoctorSchedule> findByDoctorIdAndActiveTrue(Long doctorId);

    Optional<DoctorSchedule> findByDoctorIdAndDayOfWeekAndActiveTrue(
            Long doctorId,
            DayOfWeek dayOfWeek);

    boolean existsByDoctorIdAndDayOfWeekAndActiveTrue(
            Long doctorId,
            DayOfWeek dayOfWeek);

    boolean existsByDoctorIdAndDayOfWeekAndIdNotAndActiveTrue(
            Long doctorId,
            DayOfWeek dayOfWeek,
            Long id);

    boolean existsByDoctorIdAndDayOfWeekAndStartTimeLessThanAndEndTimeGreaterThanAndActiveTrue(
            Long doctorId,
            DayOfWeek dayOfWeek,
            LocalTime endTime,
            LocalTime startTime);

    boolean existsByDoctorIdAndDayOfWeekAndStartTimeLessThanAndEndTimeGreaterThanAndIdNotAndActiveTrue(
            Long doctorId,
            DayOfWeek dayOfWeek,
            LocalTime endTime,
            LocalTime startTime,
            Long id);
}