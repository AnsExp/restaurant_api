package com.restaurant.repositories;

import com.restaurant.entities.Client;
import com.restaurant.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByClient(Client client);
}
