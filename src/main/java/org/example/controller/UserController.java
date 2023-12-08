package org.example.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/client")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String getIndex() {
        return "index";
    }

    @PostMapping("")
    public String doOrder(Model model, HttpServletRequest request, @RequestParam("orderInfo") String orderInfo) {
        List<Cookie> cookies = Arrays.stream(request.getCookies()).toList();
        model.addAttribute("state", userService.doOrder(userService.getIDFromCookie(cookies), orderInfo));
        return "index";
    }

    @GetMapping("/orders")
    public String getOrders(Model model, HttpServletRequest request) {
        List<Cookie> cookies = Arrays.stream(request.getCookies()).toList();
        model.addAttribute("orders", userService.getAllOrders(userService.getIDFromCookie(cookies)));
        return "userOrders";
    }

}
