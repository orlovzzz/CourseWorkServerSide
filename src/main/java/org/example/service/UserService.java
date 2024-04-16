package org.example.service;

import org.example.dto.ClientOrderDTO;
import org.example.dto.OrderDTO;
import org.example.dto.SuccessMessageDTO;
import org.example.entity.Client;
import org.example.entity.Order;
import org.example.repository.ClientRepository;
import org.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private EmailService emailService;

    public SuccessMessageDTO doOrder(ClientOrderDTO orderDTO) {
        Integer id = Integer.parseInt(orderDTO.getId());
        Client client = clientRepository.findById(id).orElse(null);
        Order order = new Order(client, orderDTO.getOrderDescription(), "Send");
        orderRepository.save(order);
        Thread sendMailThread = new Thread(() -> emailService.sendEmail(client.getEmail(), "Order created",
                "Your order with description\n" + orderDTO.getOrderDescription() + "\nhas been created"));
        sendMailThread.start();
        return new SuccessMessageDTO(true, "Order created");
    }

    public List<OrderDTO> getAllOrders(String idS) {
        Integer id = Integer.parseInt(idS);
        Client client = clientRepository.findById(id).orElse(null);
        List<Order> order = orderRepository.findByClient(client);
        List<OrderDTO> ordersDTO = new ArrayList<>();
        for (Order o : order) {
            ordersDTO.add(new OrderDTO(o.getId(), o.getOrderInfo(), o.getOrderPrice(), o.getOrderState(), o.getEmployees()));
        }
        return ordersDTO;
    }

}
