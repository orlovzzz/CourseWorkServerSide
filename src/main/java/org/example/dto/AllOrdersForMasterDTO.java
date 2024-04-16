package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.Client;

@Getter
@Setter
public class AllOrdersForMasterDTO extends OrderDTO {

    private Client client;
    private String state;

    public AllOrdersForMasterDTO(Client client, String orderDescription, int id) {
        this.id = id;
        this.client = client;
        this.orderDescription = orderDescription;
    }

    public AllOrdersForMasterDTO(Client client, String orderDescription, int id, String state) {
        this.id = id;
        this.client = client;
        this.orderDescription = orderDescription;
        this.state = state;
    }

}
