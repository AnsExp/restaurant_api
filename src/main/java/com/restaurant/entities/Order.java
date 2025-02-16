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
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "`table`", nullable = false)
    private int table;
    @Column(columnDefinition = "DATETIME DEFAULT (CURRENT_DATE)", name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;
    @OneToMany(targetEntity = OrderItem.class, mappedBy = "order")
    private List<OrderItem> items;
    @Column(columnDefinition = "TEXT(1000)")
    private String message;
}
