package com.hrms.hrpitch.onboarding.controller;

import com.hrms.hrpitch.common.dto.LoginData;
import com.hrms.hrpitch.onboarding.dto.ClientDetailsData;
import com.hrms.hrpitch.onboarding.service.ClientOnboardingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;


@RestController
@RequestMapping("/api/public/client")
@Validated
public class
ClientOnBoardingController {
    @Autowired
    ClientOnboardingService service;

    @PostMapping("/sign-up")
    public ResponseEntity<String> addNewClient(@Valid @RequestBody ClientDetailsData clientDetailsData) throws IOException, SQLException {
        service.addClient(clientDetailsData);
        return ResponseEntity.ok().body("Success");
    }

    @PostMapping("/create-admin")
    public ResponseEntity<String> addClientAdmin(@Valid @RequestBody LoginData loginData){
        service.addAdminCreds(loginData);
        return ResponseEntity.ok().body("Success");
    }
}
