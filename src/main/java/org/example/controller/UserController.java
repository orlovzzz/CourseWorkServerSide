package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.dto.ClientOrderDTO;
import org.example.dto.OrderDTO;
import org.example.dto.SuccessMessageDTO;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/client")
@Tag(name = "UserController", description = "Interacting with user data")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    @Operation(summary = "Make an order")
    public ResponseEntity<SuccessMessageDTO> doOrder(@RequestBody ClientOrderDTO orderDTO) {
        return new ResponseEntity<>(userService.doOrder(orderDTO), HttpStatus.OK);
    }

    @GetMapping("")
    @Operation(summary = "Get all client orders")
    public ResponseEntity<List<OrderDTO>> getOrders(@RequestParam("id") String id) {
        return new ResponseEntity<>(userService.getAllOrders(id), HttpStatus.OK);
    }

}
