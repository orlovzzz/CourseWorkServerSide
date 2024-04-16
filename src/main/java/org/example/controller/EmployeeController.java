package org.example.controller;

import org.example.dto.*;
import org.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/master")
    public ResponseEntity<List<AllOrdersForMasterDTO>> getAllOrdersWithoutMaster() {
        return new ResponseEntity<>(employeeService.getAllOrdersWithoutMaster(), HttpStatus.OK);
    }

    @PostMapping("/master")
    public ResponseEntity<SuccessMessageDTO> setMasterToOrder(@RequestBody SetMasterDTO setMaster) {
        return new ResponseEntity<>(employeeService.setMasterToOrder(setMaster), HttpStatus.OK);
    }

    @GetMapping("/master/orders")
    public ResponseEntity<List<AllOrdersForMasterDTO>> getAllMasterOrders(@RequestParam("masterId") String id) {
        return new ResponseEntity<>(employeeService.getAllMasterOrders(id), HttpStatus.OK);
    }

    @PostMapping("/master/orders")
    public ResponseEntity<SuccessMessageDTO> setCompleteStateToOrder(@RequestParam("orderId") String id) {
        return new ResponseEntity<>(employeeService.setCompleteStateToOrder(id), HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<ClientsForAdminPanelDTO>> getAllClients() {
        return new ResponseEntity<>(employeeService.getAllClients(), HttpStatus.OK);
    }

    @GetMapping("/getOrders")
    public ResponseEntity<List<AdminOrderDTO>> getAllOrdersByClientId(@RequestParam("clientId") String id) {
        return new ResponseEntity<>(employeeService.getOrdersByClientId(id), HttpStatus.OK);
    }

    @PostMapping("/getOrders")
    public ResponseEntity deleteOrder(@RequestParam("orderId") String id, @RequestParam("adminId") String adminId) {
        employeeService.deleteOrder(id, adminId);
        return new ResponseEntity(HttpStatus.OK);
    }
}