package com.tech.soft.health_care_svc.common.util;

import java.time.Year;

public final class CodeGenerator {

    public static String generate(String prefix,Long id) {
        int year = Year.now().getValue();
        String suffixId = "-"+year+"-"+String.format("%06d", id);
        return prefix + suffixId;
    }
}