package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entity.Employee;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDTO {

    protected int id;
    protected String orderDescription;
    protected double orderPrice;
    protected String orderState;
    private Employee employees;

}
