package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.dto.*;
import org.example.service.EmailService;
import org.example.service.LoginRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Tag(name = "LoginRegistrationController", description = "Login or registration user or employee")
public class LoginRegistrationController {

    @Autowired
    private LoginRegistrationService service;
    @Autowired
    private EmailService emailService;

    @PostMapping("/registration/client")
    @Operation(summary = "Client registration")
    public ResponseEntity<SuccessMessageDTO> registrationUser(@RequestBody AccountRegistrationClientDTO accountReg) {
        SuccessMessageDTO successMessageDTO = service.registrationUser(accountReg);
        if (successMessageDTO.isSuccess()) {
            return new ResponseEntity<>(successMessageDTO, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(successMessageDTO, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/registration/employee")
    @Operation(summary = "Employee registration")
    public ResponseEntity<SuccessMessageDTO> registrationEmployee(@RequestBody AccountRegistrationEmployeeDTO employeeReg) {
        SuccessMessageDTO successMessageDTO = service.registrationEmployee(employeeReg);
        if (successMessageDTO.isSuccess()) {
            return new ResponseEntity<>(successMessageDTO, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(successMessageDTO, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login/client")
    @Operation(summary = "Client login")
    public ResponseEntity<SuccessMessageDTO> loginUser(@RequestBody AccountClientLoginDTO accountClient) {
        SuccessMessageDTO successMessageDTO = service.loginUser(accountClient);
        if (successMessageDTO.isSuccess()) {
            return new ResponseEntity<>(successMessageDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(successMessageDTO, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/login/employee")
    @Operation(summary = "Employee login")
    public ResponseEntity<SuccessMessageEmployeeDTO> loginEmployee(@RequestBody AccountEmployeeLoginDTO accountEmployee) {
        SuccessMessageEmployeeDTO successMessageEmployeeDTO = service.loginEmployee(accountEmployee);
        if (successMessageEmployeeDTO.isSuccess()) {
            return new ResponseEntity<>(successMessageEmployeeDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(successMessageEmployeeDTO, HttpStatus.UNAUTHORIZED);
    }
}