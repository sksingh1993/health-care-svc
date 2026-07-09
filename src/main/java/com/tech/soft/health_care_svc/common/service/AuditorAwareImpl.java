package com.tech.soft.health_care_svc.common.service;

import com.tech.soft.health_care_svc.common.dto.CustomUserPrincipal;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
            !authentication.isAuthenticated() ||
            authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        CustomUserPrincipal principal =
                (CustomUserPrincipal) authentication.getPrincipal();

        return Optional.of(principal.getUserId());
    }
}