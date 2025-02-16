package com.restaurant.validators;

import com.restaurant.requests.DishRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DishRequestValidator implements Validator<DishRequest> {

    private final List<String> stack;

    public DishRequestValidator() {
        stack = new ArrayList<>();
    }

    @Override
    public List<String> errorStack() {
        return stack;
    }

    @Override
    public boolean test(DishRequest dishRequest) {

        stack.clear();

        boolean pass = true;

        if (dishRequest == null) {
            stack.add("Request invalid");
            return false;
        }

        if (dishRequest.name() == null) {
            stack.add("Name is null.");
            pass = false;
        } else if (dishRequest.name().length() > 100) {
            stack.add("Name so large.");
            pass = false;
        }

        if (dishRequest.price() == null) {
            stack.add("Price is null.");
            pass = false;
        }

        return pass;
    }
}
