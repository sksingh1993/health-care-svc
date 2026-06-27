package com.tech.soft.health_care_svc.patient.mapper;


import com.tech.soft.health_care_svc.patient.dto.request.PatientRequest;
import com.tech.soft.health_care_svc.patient.dto.request.PatientUpdateRequest;
import com.tech.soft.health_care_svc.patient.dto.response.PatientResponse;
import com.tech.soft.health_care_svc.patient.entity.Patient;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patientCode", ignore = true)
    @Mapping(target = "active", constant = "true")
    Patient toEntity(PatientRequest request);

//    @Mapping(target = "fullName",
//            expression = "java(getFullName(patient))")
    @Mapping(target = "age",
            expression = "java(calculateAge(patient))")
    PatientResponse toResponse(Patient patient);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(
            PatientUpdateRequest request,
            @MappingTarget Patient patient);

    default Integer calculateAge(Patient patient) {
        if (patient.getDateOfBirth() == null) {
            return null;
        }

        return java.time.Period.between(
                patient.getDateOfBirth(),
                java.time.LocalDate.now()).getYears();
    }

    default String getFullName(Patient patient) {
        return patient.getLastName() == null
                ? patient.getFirstName()
                : patient.getFirstName() + " " + patient.getLastName();
    }
}