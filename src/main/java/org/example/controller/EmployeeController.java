package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.dto.*;
import org.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Tag(name = "EmployeeController", description = "Interaction with employee data")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/master")
    @Operation(summary = "Get all orders without master")
    public ResponseEntity<List<AllOrdersForMasterDTO>> getAllOrdersWithoutMaster() {
        return new ResponseEntity<>(employeeService.getAllOrdersWithoutMaster(), HttpStatus.OK);
    }

    @PostMapping("/master")
    @Operation(summary = "Set master to order")
    public ResponseEntity<SuccessMessageDTO> setMasterToOrder(@RequestBody SetMasterDTO setMaster) {
        return new ResponseEntity<>(employeeService.setMasterToOrder(setMaster), HttpStatus.OK);
    }

    @GetMapping("/master/orders")
    @Operation(summary = "Get all master`s orders")
    public ResponseEntity<List<AllOrdersForMasterDTO>> getAllMasterOrders(@RequestParam("masterId") String id) {
        return new ResponseEntity<>(employeeService.getAllMasterOrders(id), HttpStatus.OK);
    }

    @PostMapping("/master/orders")
    @Operation(summary = "Set order state on \"Complete\"")
    public ResponseEntity<SuccessMessageDTO> setCompleteStateToOrder(@RequestParam("orderId") String id) {
        return new ResponseEntity<>(employeeService.setCompleteStateToOrder(id), HttpStatus.OK);
    }

    @GetMapping("/admin")
    @Operation(summary = "Get all clients for admin page")
    public ResponseEntity<List<ClientsForAdminPanelDTO>> getAllClients() {
        return new ResponseEntity<>(employeeService.getAllClients(), HttpStatus.OK);
    }

    @GetMapping("/getOrders")
    @Operation(summary = "Get all orders by client ID for admin page")
    public ResponseEntity<List<AdminOrderDTO>> getAllOrdersByClientId(@RequestParam("clientId") String id) {
        return new ResponseEntity<>(employeeService.getOrdersByClientId(id), HttpStatus.OK);
    }

    @PostMapping("/getOrders")
    @Operation(summary = "Delete order by ID")
    public ResponseEntity deleteOrder(@RequestParam("orderId") String id, @RequestParam("adminId") String adminId) {
        employeeService.deleteOrder(id, adminId);
        return new ResponseEntity(HttpStatus.OK);
    }
}