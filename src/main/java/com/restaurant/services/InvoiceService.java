package com.restaurant.services;

import com.restaurant.entities.Invoice;
import com.restaurant.repositories.ClientRepository;
import com.restaurant.repositories.OrderRepository;
import com.restaurant.requests.InvoiceRequest;
import com.restaurant.responses.InvoiceResponse;
import com.restaurant.utils.Formatter;
import com.restaurant.utils.OrderUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.restaurant.repositories.InvoiceRepository;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvoiceService {

    @Value("${constants.iva}")
    private double iva;
    @Value("${constants.pagination.size}")
    private int pageSize;

    private final InvoiceRepository invoiceRepository;
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, ClientRepository clientRepository, OrderRepository orderRepository) {
        this.invoiceRepository = invoiceRepository;
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
    }

    public InvoiceResponse save(InvoiceRequest invoiceRequest) {
        Invoice invoice = convertRequestToEntity(invoiceRequest);
        return convertEntityToResponse(invoiceRepository.save(invoice));
    }

    public InvoiceResponse find(long id) {
        var invoice = invoiceRepository.findById(id).orElse(null);
        if (invoice == null) {
            return null;
        }
        return convertEntityToResponse(invoice);
    }

    public List<InvoiceResponse> list() {
        return invoiceRepository.findAll().stream().map(this::convertEntityToResponse).toList();
    }

    public List<InvoiceResponse> list(String field) {
        return invoiceRepository.findAll(Sort.by(field)).stream().map(this::convertEntityToResponse).toList();
    }

    public List<InvoiceResponse> list(int page) {
        return invoiceRepository.findAll(PageRequest.of(page, pageSize)).map(this::convertEntityToResponse).toList();
    }

    public List<InvoiceResponse> list(int page, String field) {
        return invoiceRepository.findAll(PageRequest.of(page, pageSize, Sort.by(field))).map(this::convertEntityToResponse).toList();
    }

    private Invoice convertRequestToEntity(InvoiceRequest invoiceRequest) {
        var client = clientRepository.findById(invoiceRequest.clientId());
        var order = orderRepository.findById(invoiceRequest.orderId());
        client.ifPresent(cl -> cl.setInvoices(List.of()));
        return Invoice
                .builder()
                .iva(iva)
                .order(order.orElse(null))
                .client(client.orElse(null))
                .dateCreation(LocalDateTime.now())
                .build();
    }

    private InvoiceResponse convertEntityToResponse(Invoice invoice) {
        long subtotal = OrderUtil.calculateSubtotal(invoice.getOrder());
        return InvoiceResponse
                .builder()
                .id(invoice.getId())
                .iva(Formatter.prettyPercent(iva))
                .client(InvoiceResponse.InvoiceResponseClient
                        .builder()
                        .phone(invoice.getClient().getPhone())
                        .email(invoice.getClient().getEmail())
                        .idCard(invoice.getClient().getIdCard())
                        .lastname(invoice.getClient().getLastname())
                        .firstname(invoice.getClient().getFirstname())
                        .build())
                .subtotal(Formatter.prettyCurrency(subtotal))
                .date(Formatter.prettyDate(invoice.getDateCreation()))
                .total(Formatter.prettyCurrency((long) (subtotal * (iva + 1))))
                .build();
    }
}
