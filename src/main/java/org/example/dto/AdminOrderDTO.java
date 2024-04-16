package org.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminOrderDTO extends OrderDTO {

    private int employeeId;
    private int clientId;

    public AdminOrderDTO(int employeeId, int clientId, int id, String orderDescription, String orderState, double orderPrice) {
        this.employeeId = employeeId;
        this.clientId = clientId;
        this.orderDescription = orderDescription;
        this.orderState = orderState;
        this.orderPrice = orderPrice;
        this.id = id;
    }

}
