package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "zakaz")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @Column(name = "order_inf")
    private String orderInfo;

    @Column(name = "order_price")
    private double orderPrice;

    @Column(name = "order_state")
    private String orderState;

    @ManyToOne
    @JoinColumn(name = "id_master")
    private Employee employees;

}
