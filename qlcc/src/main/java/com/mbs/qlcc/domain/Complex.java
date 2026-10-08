package com.mbs.qlcc.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "complex")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Complex extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "complex_name", nullable = false, unique = true)
    private String complexName;

    @Column(name = "address", nullable = false, unique = true)
    private String address;

    @Column(name = "total_building", nullable = false)
    private int totalBuilding;

    @Column(name = "total_apartment", nullable = false)
    private int totalApartment;

    @Column(name = "name_contact", nullable = false)
    private String nameContact;

    @Column(name = "phone_contact", nullable = false, unique = true)
    private String phoneContact;

    @Column(name = "email_contact")
    private String emailContact;

    @Column(name = "description")
    private String description;

    @Column(name = "financial_model")
    private String financialModel;

    @Column(name = "status", nullable = false)
    private int status;  // 0: pending, 1: approved, 2: rejected
}

