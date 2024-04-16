package org.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRegistrationEmployeeDTO {

    private String name;
    private String surname;
    private String login;
    private String password;
    private String position;
    private Integer uniqueCode;

}
