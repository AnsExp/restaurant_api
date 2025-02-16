package com.restaurant.validators;

import com.restaurant.requests.OrderRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderRequestValidator implements Validator<OrderRequest> {

    private final List<String> stack;

    public OrderRequestValidator() {
        stack = new ArrayList<>();
    }

    @Override
    public List<String> errorStack() {
        return stack;
    }

    @Override
    public boolean test(OrderRequest orderRequest) {

        stack.clear();

        boolean pass = true;

        if (orderRequest == null) {
            stack.add("Request invalid");
            return false;
        }

        if (orderRequest.table() == 0) {
            stack.add("Table number is zero");
            pass = false;
        }

        if (orderRequest.message() != null && orderRequest.message().length() > 1000) {
            stack.add("Message no large");
            pass = false;
        }

        if (orderRequest.dishes() == null) {
            stack.add("Order items is null");
            pass = false;
        } else if (orderRequest.dishes().isEmpty()) {
            stack.add("Order items empty");
            pass = false;
        }

        return pass;
    }
}
