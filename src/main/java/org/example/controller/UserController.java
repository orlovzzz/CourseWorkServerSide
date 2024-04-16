package org.example.controller;

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
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<SuccessMessageDTO> doOrder(@RequestBody ClientOrderDTO orderDTO) {
        return new ResponseEntity<>(userService.doOrder(orderDTO), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<OrderDTO>> getOrders(@RequestParam("id") String id) {
        return new ResponseEntity<>(userService.getAllOrders(id), HttpStatus.OK);
    }

}
