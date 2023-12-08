package org.example.service;

import jakarta.servlet.http.Cookie;
import org.example.entity.Client;
import org.example.entity.Employee;
import org.example.entity.Order;
import org.example.repository.ClientRepository;
import org.example.repository.EmployeeRepository;
import org.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ClientRepository clientRepository;

    public String getIDFromCookie(List<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("employeeID")) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public boolean getPosition(String idS) {
        Integer id = Integer.parseInt(idS);
        return employeeRepository.findById(id).orElse(null).getPosition().equals("Administrator") ? true : false;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public List<Order> getOrdersByClientID(String idS) {
        Integer id = Integer.parseInt(idS);
        Client client = clientRepository.findById(id).orElse(null);
        return orderRepository.findByClient(client);
    }

    public void deleteOrderByID(String idS) {
        Integer id = Integer.parseInt(idS);
        orderRepository.deleteById(id);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findByOrderState("Send");
    }

    public void addMasterToOrder(String orderS, String priceS, String masterS) {
        Integer masterID = Integer.parseInt(masterS);
        Integer orderID = Integer.parseInt(orderS);
        Double price = Double.parseDouble(priceS);
        Employee employee = employeeRepository.findById(masterID).orElse(null);
        Order order = orderRepository.findById(orderID).orElse(null);
        order.setEmployees(employee);
        order.setOrderPrice(price);
        order.setOrderState("In Work");
        orderRepository.save(order);
    }

    public List<Order> getMasterOrders(String masterS) {
        Integer masterID = Integer.parseInt(masterS);
        Employee employee = employeeRepository.findById(masterID).orElse(null);
        return orderRepository.findByEmployees(employee);
    }

    public void orderReady(String orderS) {
        Integer orderID = Integer.parseInt(orderS);
        Order order = orderRepository.findById(orderID).orElse(null);
        order.setOrderState("Completed");
        orderRepository.save(order);
    }

}
