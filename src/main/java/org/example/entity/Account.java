package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String login;

    private String password;

    @Column(name = "is_employee")
    private boolean isEmployee;

    public Account(String login, String password, boolean isEmployee) {
        this.login = login;
        this.password = password;
        this.isEmployee = isEmployee;
    }

}
