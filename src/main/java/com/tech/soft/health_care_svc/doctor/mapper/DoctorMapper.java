package com.tech.soft.health_care_svc.doctor.mapper;


import com.tech.soft.health_care_svc.doctor.dto.request.DoctorRequest;
import com.tech.soft.health_care_svc.doctor.dto.request.DoctorUpdateRequest;
import com.tech.soft.health_care_svc.doctor.dto.response.DoctorResponse;
import com.tech.soft.health_care_svc.doctor.entity.Doctor;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "doctorCode", ignore = true)
    @Mapping(target = "active", constant = "true")
    Doctor toEntity(DoctorRequest request);

    @Mapping(target = "fullName",
            expression = "java(getFullName(doctor))")
    @Mapping(target = "age",
            expression = "java(calculateAge(doctor))")
    DoctorResponse toResponse(Doctor doctor);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(
            DoctorUpdateRequest request,
            @MappingTarget Doctor doctor);

    default String getFullName(Doctor doctor) {

        if (doctor.getLastName() == null ||
                doctor.getLastName().isBlank()) {
            return doctor.getFirstName();
        }

        return doctor.getFirstName() + " " + doctor.getLastName();
    }

    default Integer calculateAge(Doctor doctor) {

        if (doctor.getDateOfBirth() == null) {
            return null;
        }

        return java.time.Period.between(
                doctor.getDateOfBirth(),
                java.time.LocalDate.now())
                .getYears();
    }
}