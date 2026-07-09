package com.tech.soft.health_care_svc.appointment.mapper;

import com.tech.soft.health_care_svc.appointment.dto.request.AppointmentRequest;
import com.tech.soft.health_care_svc.appointment.dto.request.AppointmentUpdateRequest;
import com.tech.soft.health_care_svc.appointment.dto.response.AppointmentResponse;
import com.tech.soft.health_care_svc.appointment.entity.Appointment;
import com.tech.soft.health_care_svc.doctor.entity.Doctor;
import com.tech.soft.health_care_svc.patient.entity.Patient;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appointmentCode", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "appointmentEndTime", ignore = true)
    @Mapping(target = "status", constant = "BOOKED")
    @Mapping(target = "active", constant = "true")
    Appointment toEntity(AppointmentRequest request);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "appointmentCode", ignore = true)
    void updateEntity(
            AppointmentUpdateRequest request,
            @MappingTarget Appointment appointment);

    @Mapping(source = "patient.patientCode",target = "patientCode")
    @Mapping(source = "patient.id",target = "patientId")
    @Mapping(target = "patientName",
            expression = "java(getPatientFullName(appointment.getPatient()))")
    //@Mapping(source = "patient.patientName",target = "patientName")
    @Mapping(source = "doctor.id",target = "doctorId" )
    @Mapping(source = "doctor.doctorCode",target = "doctorCode")
    @Mapping(target = "doctorName",
            expression = "java(getDoctorFullName(appointment.getDoctor()))")
    //@Mapping(source = "doctor.doctorName",target = "doctorName")
    AppointmentResponse toResponse(Appointment appointment);


    default String getDoctorFullName(Doctor doctor) {

        if (doctor.getLastName() == null ||
                doctor.getLastName().isBlank()) {
            return doctor.getFirstName();
        }

        return doctor.getFirstName() + " " + doctor.getLastName();
    }

    default String getPatientFullName(Patient patient) {

        if (patient.getLastName() == null ||
                patient.getLastName().isBlank()) {
            return patient.getFirstName();
        }

        return patient.getFirstName() + " " + patient.getLastName();
    }
}