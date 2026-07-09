package com.tech.soft.health_care_svc.doctor.dto.request;


import com.tech.soft.health_care_svc.patient.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
    private String username;

    @NotBlank(message = "Password is required")
    //@Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    @NotBlank(message = "First name is required")
    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$",
            message = "Invalid mobile number")
    private String mobile;

    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Specialization is required")
    private String specialization;

    private String qualification;

    @Min(value = 0, message = "Experience cannot be negative")
    private Integer experienceYears;

    @NotNull(message = "Consultation fee is required")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "Consultation fee must be greater than zero")
    private BigDecimal consultationFee;

    @NotBlank(message = "Registration number is required")
    private String registrationNumber;

    private String address;
}