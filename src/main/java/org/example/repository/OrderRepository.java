package org.example.repository;

import org.example.entity.Client;
import org.example.entity.Employee;
import org.example.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByClient(Client client);
    List<Order> findByOrderState(String orderState);
    List<Order> findByEmployees(Employee employees);
}
