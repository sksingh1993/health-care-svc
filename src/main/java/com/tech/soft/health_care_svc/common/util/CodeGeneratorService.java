package com.tech.soft.health_care_svc.common.util;

public interface CodeGeneratorService {

    String generatePatientCode();

    String generateDoctorCode();

    String generateAppointmentCode();

    String generateBillCode();

    String generatePrescriptionCode();

    String generatePaymentCode();
}