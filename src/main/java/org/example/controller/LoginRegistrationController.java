package org.example.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.Account;
import org.example.entity.Client;
import org.example.entity.Employee;
import org.example.entity.Success;
import org.example.service.LoginRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginRegistrationController {

    @Autowired
    private LoginRegistrationService service;

    @GetMapping("")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistration() {
        return "registration";
    }

    @PostMapping("/registration/user")
    public String registrationUser(Model model, @RequestParam("name") String name, @RequestParam("surname") String surname,
                                   @RequestParam("login") String login, @RequestParam("password") String password) {
        Client client = new Client(name, surname);
        Account account = new Account(login, password, false);
        String error = service.registrationUser(client, account);
        if (error != null) {
            model.addAttribute("errorUser", error);
            return "registration";
        }
        return "redirect:/";
    }

    @PostMapping("/registration/employee")
    public String registrationEmployee(Model model, @RequestParam("name") String name, @RequestParam("surname") String surname,
                                       @RequestParam("login") String login, @RequestParam("position") String position,
                                       @RequestParam("password") String password,
                                       @RequestParam("employeePassword") String employeePassword) {
        Employee employee = new Employee(name, surname, position);
        Account account = new Account(login, password, true);
        String error = service.registrationEmployee(employee, account, employeePassword);
        if (error != null) {
            model.addAttribute("errorEmployee", error);
            return "registration";
        }
        return "redirect:/";
    }

    @PostMapping("/login/user")
    public String loginUser(Model model, HttpServletResponse response,
                            @RequestParam("login") String login, @RequestParam("password") String password) {
        Success success = service.loginUser(new Account(login, password, false));
        if (!success.success) {
            model.addAttribute("errorUser", success.message);
            return "login";
        }
        Cookie cookie = new Cookie("userID", success.message);
        cookie.setMaxAge(1000000000);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/client";
    }

    @PostMapping("/login/employee")
    public String loginEmployee(Model model, HttpServletResponse response,
                                @RequestParam("login") String login, @RequestParam("password") String password,
                                @RequestParam("position") String position, @RequestParam("uniqueCode") int uniqueCode) {
        Success success = service.loginEmployee(new Account(login, password, true), uniqueCode, position);
        if (!success.success) {
            model.addAttribute("errorEmployee", success.message);
            return "login";
        }
        Cookie cookie = new Cookie("employeeID", success.message);
        cookie.setMaxAge(1000000000);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/employee";
    }
}