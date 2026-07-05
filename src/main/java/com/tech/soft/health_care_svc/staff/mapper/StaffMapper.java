package com.tech.soft.health_care_svc.staff.mapper;


import com.tech.soft.health_care_svc.staff.dto.request.CreateStaffRequest;
import com.tech.soft.health_care_svc.staff.dto.request.StaffUpdateRequest;
import com.tech.soft.health_care_svc.staff.dto.response.StaffResponse;
import com.tech.soft.health_care_svc.staff.entity.Staff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StaffMapper {
    @Mapping(target = "designation", source = "role")
    Staff toEntity(CreateStaffRequest request);
    @Mapping(target = "userId",
    expression = "java(getUserId(staff))")
    StaffResponse toResponse(Staff staff);

    void updateEntity(StaffUpdateRequest request,
                      @MappingTarget Staff staff);

    default Long getUserId(Staff staff){
        return staff.getUser().getId();
    }
}