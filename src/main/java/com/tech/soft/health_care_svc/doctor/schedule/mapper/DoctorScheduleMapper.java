package com.tech.soft.health_care_svc.doctor.schedule.mapper;


import com.tech.soft.health_care_svc.doctor.schedule.dto.request.DoctorScheduleRequest;
import com.tech.soft.health_care_svc.doctor.schedule.dto.request.DoctorScheduleUpdateRequest;
import com.tech.soft.health_care_svc.doctor.schedule.dto.response.DoctorScheduleResponse;
import com.tech.soft.health_care_svc.doctor.schedule.entity.DoctorSchedule;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DoctorScheduleMapper {

    DoctorSchedule toEntity(DoctorScheduleRequest request);

    @Mapping(target = "doctorId", source = "doctor.id")
    @Mapping(target = "doctorCode", source = "doctor.doctorCode")
    @Mapping(target = "doctorName",
            expression = "java(entity.getDoctor().getFirstName() + \" \" + entity.getDoctor().getLastName())")
    DoctorScheduleResponse toResponse(DoctorSchedule entity);

    @BeanMapping(nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(DoctorScheduleUpdateRequest request,
                      @MappingTarget DoctorSchedule entity);
}