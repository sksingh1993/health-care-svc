package com.tech.soft.health_care_svc.appointment.repository;


import com.tech.soft.health_care_svc.appointment.entity.Appointment;
import com.tech.soft.health_care_svc.appointment.enums.AppointmentStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends
        JpaRepository<Appointment, Long>,
        JpaSpecificationExecutor<Appointment> {

    Optional<Appointment> findByAppointmentCode(String appointmentCode);

    List<Appointment> findByDoctorIdAndAppointmentDate(
            Long doctorId,
            LocalDate appointmentDate);

    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByDoctorIdAndAppointmentDateAndStatusIn(
            Long doctorId,
            LocalDate appointmentDate,
            Collection<AppointmentStatus> statuses);

    boolean existsByDoctorIdAndAppointmentDateAndAppointmentTimeAndStatusIn(
            Long doctorId,
            LocalDate appointmentDate,
            LocalTime appointmentTime,
            Collection<AppointmentStatus> statuses);

    boolean existsByDoctorIdAndAppointmentDateAndAppointmentTimeAndStatus(
            Long doctorId,
            LocalDate appointmentDate,
            LocalTime appointmentTime,
            AppointmentStatus statuses);

    boolean existsByPatientIdAndAppointmentDateAndAppointmentTimeAndStatusIn(
            Long patientId,
            LocalDate appointmentDate,
            LocalTime appointmentTime,
            Collection<AppointmentStatus> statuses);

    Optional<Appointment> findByIdAndActiveTrue(Long id);
    List<Appointment> findByDoctorIdAndAppointmentDateOrderByAppointmentTimeAsc(
            Long doctorId,
            LocalDate appointmentDate);
    List<Appointment> findByPatientIdOrderByAppointmentDateDesc(
            Long patientId);

    long countByDoctorIdAndAppointmentDateAndAppointmentTimeAndStatusIn(
            Long doctorId,
            LocalDate appointmentDate,
            LocalTime appointmentTime,
            Collection<AppointmentStatus> statuses);

    boolean existsByDoctorIdAndPatientIdAndAppointmentDateAndAppointmentTime(@NotNull Long doctorId, @NotNull Long patientId, @NotNull @FutureOrPresent LocalDate appointmentDate, @NotNull LocalTime appointmentTime);
}