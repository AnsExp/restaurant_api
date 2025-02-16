package com.restaurant.repositories;

import com.restaurant.entities.Client;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByIdCard(String idCard);
}
