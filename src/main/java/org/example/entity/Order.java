package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "zakaz")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_client")
    private Client client;

    @Column(name = "order_inf")
    private String orderInfo;

    @Column(name = "order_price")
    private double orderPrice;

    @Column(name = "order_state")
    private String orderState;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "id_master")
    private Employee employees;

    public Order(Client client, String orderInfo, String orderState) {
        this.client = client;
        this.orderInfo = orderInfo;
        this.orderState = orderState;
    }

}
