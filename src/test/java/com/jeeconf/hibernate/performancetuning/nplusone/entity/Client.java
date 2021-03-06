package com.jeeconf.hibernate.performancetuning.nplusone.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "NPlusOneClient")
@Getter
@Setter
@Table(name = "Client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_client")
    private Integer id;

    private String name;
    private int age;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<Account> accounts = new ArrayList<>();
}
