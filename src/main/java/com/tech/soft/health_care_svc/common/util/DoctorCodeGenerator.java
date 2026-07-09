package com.tech.soft.health_care_svc.common.util;

public final class DoctorCodeGenerator {

    private DoctorCodeGenerator() {
    }

    public static String generate(Long id) {
        return "DOC" + String.format("%06d", id);
    }
}