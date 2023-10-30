package com.bankingapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "account_holder")
public class AccountHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String oib;
    private String status;
}
