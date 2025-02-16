package com.restaurant.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private double iva;
    @Column(columnDefinition = "DATETIME DEFAULT (CURRENT_DATE)", name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;
    @OneToOne(targetEntity = Order.class, optional = false)
    private Order order;
    @ManyToOne(targetEntity = Client.class, optional = false)
    private Client client;
}
