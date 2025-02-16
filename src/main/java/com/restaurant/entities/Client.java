package com.restaurant.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 50, nullable = false)
    private String firstname;
    @Column(length = 50, nullable = false)
    private String lastname;
    @Column(length = 20, nullable = false)
    private String phone;
    @Column(unique = true, length = 100, nullable = false)
    private String email;
    @Column(name = "id_card", unique = true, length = 20, nullable = false)
    private String idCard;
    @Column(columnDefinition = "DATETIME DEFAULT (CURRENT_DATE)", name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;
    @OneToMany(targetEntity = Invoice.class, mappedBy = "client")
    private List<Invoice> invoices;
}
