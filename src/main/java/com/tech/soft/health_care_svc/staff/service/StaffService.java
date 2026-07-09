package com.tech.soft.health_care_svc.staff.service;



import com.tech.soft.health_care_svc.staff.dto.request.CreateStaffRequest;
import com.tech.soft.health_care_svc.staff.dto.request.StaffSearchRequest;
import com.tech.soft.health_care_svc.staff.dto.request.StaffUpdateRequest;
import com.tech.soft.health_care_svc.staff.dto.response.StaffResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StaffService {

    StaffResponse create(CreateStaffRequest request);


    public StaffResponse updateStaff(
            Long id,
            StaffUpdateRequest request);

    public StaffResponse getStaff(Long id);



    //List<StaffResponse> getAll();
    public Page<StaffResponse> searchStaff(
            StaffSearchRequest request,
            Pageable pageable);

    public void deleteStaff(Long id);
}