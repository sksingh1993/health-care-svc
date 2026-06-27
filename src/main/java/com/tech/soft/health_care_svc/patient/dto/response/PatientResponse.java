package com.tech.soft.health_care_svc.patient.dto.response;
import com.tech.soft.health_care_svc.patient.enums.BloodGroup;
import com.tech.soft.health_care_svc.patient.enums.Gender;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponse {

    private Long id;

    private String patientCode;

    private String firstName;

    private String lastName;

    private Integer age;

    private Gender gender;

    private String mobile;

    private String email;

    private BloodGroup bloodGroup;

    private String allergies;

    private String address;

    private Boolean active;
}