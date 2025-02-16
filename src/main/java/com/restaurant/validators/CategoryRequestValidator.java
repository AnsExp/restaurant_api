package com.restaurant.validators;

import com.restaurant.requests.CategoryRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryRequestValidator implements Validator<CategoryRequest> {

    private final List<String> stack;

    public CategoryRequestValidator() {
        stack = new ArrayList<>();
    }

    @Override
    public List<String> errorStack() {
        return stack;
    }

    @Override
    public boolean test(CategoryRequest categoryRequest) {

        stack.clear();

        boolean pass = true;

        if (categoryRequest == null) {
            stack.add("Request no valid");
            return false;
        }

        if (categoryRequest.name() == null) {
            stack.add("Name empty");
            pass = false;
        } else if (categoryRequest.name().length() > 50) {
            stack.add("Name so large");
            pass = false;
        }

        return pass;
    }
}
