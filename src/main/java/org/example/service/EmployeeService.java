package org.example.service;

import org.example.dto.*;
import org.example.entity.Client;
import org.example.entity.Employee;
import org.example.entity.Order;
import org.example.repository.ClientRepository;
import org.example.repository.EmployeeRepository;
import org.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private EmailService emailService;

    public List<AllOrdersForMasterDTO> getAllOrdersWithoutMaster() {
        List<Order> orders = orderRepository.findAll();
        List<AllOrdersForMasterDTO> masterOrders = new ArrayList<>();
        for (Order o : orders) {
            if (o.getEmployees() == null) {
                masterOrders.add(new AllOrdersForMasterDTO(o.getClient(), o.getOrderInfo(), o.getId()));
            }
        }
        return masterOrders;
    }

    public SuccessMessageDTO setMasterToOrder(SetMasterDTO dto) {
        Integer orderId = Integer.valueOf(dto.getOrderId());
        Integer masterId = Integer.valueOf(dto.getMasterId());
        Double price = Double.valueOf(dto.getPrice());
        Employee employee = employeeRepository.findById(masterId).orElse(null);
        Order order = orderRepository.findById(orderId).orElse(null);
        order.setOrderPrice(price);
        order.setEmployees(employee);
        order.setOrderState("In work");
        orderRepository.save(order);
        Thread sendMailThread = new Thread(() -> emailService.sendEmail(
                order.getClient().getEmail(), "Your order " + order.getId() + " in progress",
                "The master will take care of your order:" + "\nID: " + employee.getId() +
                        "\nName: " + employee.getName() + "\nSurname: " + employee.getSurname()));
        sendMailThread.start();
        return new SuccessMessageDTO(true, "");
    }

    public List<AllOrdersForMasterDTO> getAllMasterOrders(String id) {
        Employee employee = employeeRepository.findById(Integer.valueOf(id)).orElse(null);
        List<Order> orders = orderRepository.findByEmployees(employee);
        List<AllOrdersForMasterDTO> masterOrders = new ArrayList<>();
        for (Order o : orders) {
            masterOrders.add(new AllOrdersForMasterDTO(o.getClient(), o.getOrderInfo(), o.getId(), o.getOrderState()));
        }
        return masterOrders;
    }

    public SuccessMessageDTO setCompleteStateToOrder(String orderId) {
        Order order = orderRepository.findById(Integer.valueOf(orderId)).orElse(null);
        order.setOrderState("Completed");
        orderRepository.save(order);
        Thread sendEmailThread = new Thread(() ->
                emailService.sendEmail(order.getClient().getEmail(), "Your order is completed",
                        "Your order " + orderId + " is completed by master:" + "\nID: " + order.getEmployees().getId() +
                                "\nName: " + order.getEmployees().getName() +
                                "\nSurname: " + order.getEmployees().getSurname()));
        sendEmailThread.start();
        return new SuccessMessageDTO(true, "");
    }

    public List<ClientsForAdminPanelDTO> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        List<ClientsForAdminPanelDTO> clientsDTO = new ArrayList<>();
        for (Client client : clients) {
            clientsDTO.add(new ClientsForAdminPanelDTO(client.getId(), client.getName(), client.getSurname()));
        }
        return clientsDTO;
    }

    public List<AdminOrderDTO> getOrdersByClientId(String clientId) {
        List<Order> orders = orderRepository.findByClient(clientRepository.findById(Integer.valueOf(clientId)).orElse(null));
        List<AdminOrderDTO> clientOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getEmployees() != null) {
                clientOrders.add(new AdminOrderDTO(order.getEmployees().getId(),
                        order.getClient().getId(),
                        order.getId(), order.getOrderInfo(), order.getOrderState(), order.getOrderPrice()));
            } else {
                clientOrders.add(new AdminOrderDTO(0,
                        order.getClient().getId(),
                        order.getId(), order.getOrderInfo(), order.getOrderState(), order.getOrderPrice()));
            }
        }
        return clientOrders;
    }

    public void deleteOrder(String orderId, String adminId) {
        Order order = orderRepository.findById(Integer.valueOf(orderId)).orElse(null);
        orderRepository.deleteById(order.getId());
        Employee employee = employeeRepository.findById(Integer.valueOf(adminId)).orElse(null);
        Thread sendMailThread = new Thread(() ->
                emailService.sendEmail(order.getClient().getEmail(), "Your order was deleted",
                        "Your order " + orderId + " was deleted by admin: " +
                                "\nID: " + employee.getId() + "\nName: " + employee.getName() + "\nSurname: " + employee.getSurname()));
        sendMailThread.start();
    }
}
