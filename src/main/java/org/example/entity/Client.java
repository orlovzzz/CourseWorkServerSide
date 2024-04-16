package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_account")
    private Account account;

    private String email;
    private String name;

    private String surname;

    public Client(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Client(String name, String surname, String email, Account account) {
        this.name = name;
        this.email = email;
        this.surname = surname;
        this.account = account;
    }
}
