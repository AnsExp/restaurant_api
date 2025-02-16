package com.restaurant.validators;

import java.util.ArrayList;
import java.util.List;

import com.restaurant.repositories.ClientRepository;
import com.restaurant.repositories.OrderRepository;
import org.springframework.stereotype.Component;

import com.restaurant.requests.InvoiceRequest;

@Component
public class InvoiceRequestValidator implements Validator<InvoiceRequest> {

    private final List<String> stack = new ArrayList<>();
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;

    public InvoiceRequestValidator(ClientRepository clientRepository, OrderRepository orderRepository) {
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public boolean test(InvoiceRequest invoiceRequest) {

        stack.clear();

        boolean pass = true;

        if (invoiceRequest == null) {
            stack.add("Request is null");
            return false;
        }

        if (!clientRepository.existsById(invoiceRequest.clientId())) {
            stack.add("Client no exits");
            pass = false;
        }

        if (!orderRepository.existsById(invoiceRequest.orderId())) {
            stack.add("Order no exits");
            pass = false;
        }

        return pass;
    }

    @Override
    public List<String> errorStack() {
        return stack;
    }
}
