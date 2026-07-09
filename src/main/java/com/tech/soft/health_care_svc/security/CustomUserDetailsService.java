package com.tech.soft.health_care_svc.security;

import com.tech.soft.health_care_svc.auth.entity.User;
import com.tech.soft.health_care_svc.auth.repository.UserRepository;
import com.tech.soft.health_care_svc.common.dto.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(
            String username) {

        User user = repository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found"));


        List<SimpleGrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role ->
                        new SimpleGrantedAuthority(
                                "ROLE_" + role.getRoleCode()))
                .toList();

        /*return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities);*/
        return new CustomUserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
