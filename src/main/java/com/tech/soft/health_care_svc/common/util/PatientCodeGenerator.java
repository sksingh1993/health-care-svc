package com.tech.soft.health_care_svc.common.util;

public class PatientCodeGenerator {

    public static String generate(long id) {

        return "PAT" + String.format("%06d", id);
    }

}