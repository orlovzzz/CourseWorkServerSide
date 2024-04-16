package org.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountEmployeeLoginDTO {

    private String login;
    private String password;
    private String position;
    private int uniqueCode;

}
