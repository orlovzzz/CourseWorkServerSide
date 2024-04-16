package org.example.controller;

import org.example.dto.*;
import org.example.service.EmailService;
import org.example.service.LoginRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginRegistrationController {

    @Autowired
    private LoginRegistrationService service;
    @Autowired
    private EmailService emailService;

    @PostMapping("/registration/client")
    public ResponseEntity<SuccessMessageDTO> registrationUser(@RequestBody AccountRegistrationClientDTO accountReg) {
        SuccessMessageDTO successMessageDTO = service.registrationUser(accountReg);
        if (successMessageDTO.isSuccess()) {
            return new ResponseEntity<>(successMessageDTO, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(successMessageDTO, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/registration/employee")
    public ResponseEntity<SuccessMessageDTO> registrationEmployee(@RequestBody AccountRegistrationEmployeeDTO employeeReg) {
        SuccessMessageDTO successMessageDTO = service.registrationEmployee(employeeReg);
        if (successMessageDTO.isSuccess()) {
            return new ResponseEntity<>(successMessageDTO, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(successMessageDTO, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login/client")
    public ResponseEntity<SuccessMessageDTO> loginUser(@RequestBody AccountClientLoginDTO accountClient) {
        SuccessMessageDTO successMessageDTO = service.loginUser(accountClient);
        if (successMessageDTO.isSuccess()) {
            return new ResponseEntity<>(successMessageDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(successMessageDTO, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/login/employee")
    public ResponseEntity<SuccessMessageEmployeeDTO> loginEmployee(@RequestBody AccountEmployeeLoginDTO accountEmployee) {
        SuccessMessageEmployeeDTO successMessageEmployeeDTO = service.loginEmployee(accountEmployee);
        if (successMessageEmployeeDTO.isSuccess()) {
            return new ResponseEntity<>(successMessageEmployeeDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(successMessageEmployeeDTO, HttpStatus.UNAUTHORIZED);
    }
}