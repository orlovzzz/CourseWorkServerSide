package org.example.service;

import jakarta.servlet.http.Cookie;
import org.example.entity.Client;
import org.example.entity.Order;
import org.example.repository.ClientRepository;
import org.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OrderRepository orderRepository;

    public String doOrder(String idS, String orderInfo) {
        Integer id = Integer.parseInt(idS);
        Client client = clientRepository.findById(id).orElse(null);;
        Order order = new Order();
        order.setOrderInfo(orderInfo);
        order.setClient(client);
        order.setOrderState("Send");
        orderRepository.save(order);
        return "Order created";
    }

    public List<Order> getAllOrders(String idS) {
        Integer id = Integer.parseInt(idS);
        Client client = clientRepository.findById(id).orElse(null);
        return orderRepository.findByClient(client);
    }

    public String getIDFromCookie(List<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userID")) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
