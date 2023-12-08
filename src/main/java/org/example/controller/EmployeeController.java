package org.example.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.example.entity.Client;
import org.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("")
    public String proxyEmployee(HttpServletRequest request) {
        List<Cookie> cookies = Arrays.stream(request.getCookies()).toList();
        if (employeeService.getPosition(employeeService.getIDFromCookie(cookies))) {
            return "redirect:/employee/admin";
        }
        return "redirect:/employee/master";
    }

    @GetMapping("/admin")
    public String getAdminPanel(Model model) {
        List<Client> clients = employeeService.getAllClients();
        model.addAttribute("clients", clients);
        return "admin";
    }

    @GetMapping("/admin/{id}")
    public String getClientOrders(Model model, @PathVariable("id") String id) {
        System.out.println(id);
        model.addAttribute("orders", employeeService.getOrdersByClientID(id));
        return "adminClientOrders";
    }

    @GetMapping("/admin/delete/{clientID}/{id}")
    public String deleteOrder(@PathVariable("clientID") String clientID, @PathVariable("id") String id, HttpServletRequest request) {
        employeeService.deleteOrderByID(id);
        return "redirect:/employee/admin/" + clientID;
    }

    @GetMapping("/master")
    public String getMasterPage(Model model) {
        model.addAttribute("orders", employeeService.getAllOrders());
        return "master";
    }

    @PostMapping("/master/orders")
    public String setMasterToOrder(HttpServletRequest request,
                                   @RequestParam("orderId") String orderID, @RequestParam("price") String price) {
        List<Cookie> cookies = Arrays.stream(request.getCookies()).toList();
        String masterID = employeeService.getIDFromCookie(cookies);
        System.out.println(masterID);
        employeeService.addMasterToOrder(orderID, price, masterID);
        return "redirect:/employee/master";
    }

    @GetMapping("/master/orders")
    public String getMasterOrders(HttpServletRequest request, Model model) {
        List<Cookie> cookies = Arrays.stream(request.getCookies()).toList();
        String masterID = employeeService.getIDFromCookie(cookies);
        model.addAttribute("orders", employeeService.getMasterOrders(masterID));
        return "masterOrders";
    }

    @PostMapping("/master")
    public String orderReady(@RequestParam("orderId") String orderID) {
        employeeService.orderReady(orderID);
        return "redirect:/employee/master/orders";
    }

}