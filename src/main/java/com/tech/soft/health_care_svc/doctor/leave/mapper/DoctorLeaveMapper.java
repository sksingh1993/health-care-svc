package com.tech.soft.health_care_svc.doctor.leave.mapper;


import com.tech.soft.health_care_svc.doctor.entity.Doctor;
import com.tech.soft.health_care_svc.doctor.leave.dto.request.DoctorLeaveRequest;
import com.tech.soft.health_care_svc.doctor.leave.dto.request.DoctorLeaveUpdateRequest;
import com.tech.soft.health_care_svc.doctor.leave.dto.response.DoctorLeaveResponse;
import com.tech.soft.health_care_svc.doctor.leave.entity.DoctorLeave;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DoctorLeaveMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "active", constant = "true")
    DoctorLeave toEntity(DoctorLeaveRequest request);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateEntity(
            DoctorLeaveUpdateRequest request,
            @MappingTarget DoctorLeave leave);

    @Mapping(target = "doctorId", source = "doctor.id")
    @Mapping(target = "doctorCode", source = "doctor.doctorCode")
    @Mapping(target = "status", source = "leaveStatus")
    @Mapping(target = "doctorName",
            expression = "java(getDoctorName(leave.getDoctor()))")
    DoctorLeaveResponse toResponse(DoctorLeave leave);

    default String getDoctorName(Doctor doctor) {

        if (doctor == null) {
            return null;
        }

        if (doctor.getLastName() == null ||
                doctor.getLastName().isBlank()) {

            return doctor.getFirstName();
        }

        return doctor.getFirstName() + " " + doctor.getLastName();
    }
}