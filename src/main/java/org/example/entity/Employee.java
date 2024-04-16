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
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String position;

    @OneToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_account")
    private Account account;

    private String name;

    private String surname;

    @Column(name = "unique_code")
    private int uniqueCode;

    public Employee(String name, String surname, String position) {
        this.name = name;
        this.surname = surname;
        this.position = position;
    }

    public Employee(String name, String surname, String position, Account account) {
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.account = account;
    }

}