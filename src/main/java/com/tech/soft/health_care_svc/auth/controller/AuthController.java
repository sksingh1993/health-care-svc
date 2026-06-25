package com.tech.soft.health_care_svc.auth.controller;

import com.tech.soft.health_care_svc.auth.dto.CreateUserRequest;
import com.tech.soft.health_care_svc.auth.dto.LoginRequest;
import com.tech.soft.health_care_svc.auth.dto.LoginResponse;
import com.tech.soft.health_care_svc.auth.service.AuthService;
import com.tech.soft.health_care_svc.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;
    private final UserService userService;


    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        return authService.login(request);
    }
    @PostMapping("/")
    public String addUser(@RequestBody CreateUserRequest createUserRequest){
        //System.out.println("User Info "+ userInfo);
         authService.createAdmin(createUserRequest);
         return "Success";
    }
}