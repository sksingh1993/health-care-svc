package com.tech.soft.health_care_svc.staff.mapper;


import com.tech.soft.health_care_svc.patient.entity.Patient;
import com.tech.soft.health_care_svc.staff.dto.request.CreateStaffRequest;
import com.tech.soft.health_care_svc.staff.dto.request.StaffUpdateRequest;
import com.tech.soft.health_care_svc.staff.dto.response.StaffResponse;
import com.tech.soft.health_care_svc.staff.entity.Staff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StaffMapper {

    Staff toEntity(CreateStaffRequest request);
    @Mapping(target = "userId",
    expression = "java(getUserId(staff))")
    @Mapping(target = "fullName",
    expression = "java(getFullName(staff))")
    @Mapping(target = "age",
    expression = "java(calculateAge(staff))")
    StaffResponse toResponse(Staff staff);

    void updateEntity(StaffUpdateRequest request,
                      @MappingTarget Staff staff);

    default Long getUserId(Staff staff){
        return staff.getUser().getId();
    }

    default String getFullName(Staff staff){
        return staff.getFirstName()+" "+staff.getLastName();
    }
    default Integer calculateAge(Staff staff) {
        if (staff.getDateOfBirth() == null) {
            return null;
        }

        return java.time.Period.between(
                staff.getDateOfBirth(),
                java.time.LocalDate.now()).getYears();
    }

}