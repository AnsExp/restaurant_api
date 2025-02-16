package com.restaurant.validators;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.restaurant.requests.ClientRequest;

@Component
public class ClientRequestValidator implements Validator<ClientRequest> {

    private final List<String> stack = new ArrayList<>();

    @Override
    public boolean test(ClientRequest clientRequest) {

        stack.clear();

        if (clientRequest == null) {
            stack.add("Request is null");
            return false;
        }

        boolean pass = true;

        if (clientRequest.firstname() == null) {
            stack.add("Firstname is null");
            pass = false;
        } else if (clientRequest.firstname().length() > 50) {
            stack.add("Firstname so large");
            pass = false;
        }

        if (clientRequest.lastname() == null) {
            stack.add("Lastname is null");
            pass = false;
        } else if (clientRequest.lastname().length() > 50) {
            stack.add("Lastname so large");
            pass = false;
        }

        if (clientRequest.phone() == null) {
            stack.add("Phone is null");
            pass = false;
        } else if (clientRequest.phone().length() > 50) {
            stack.add("Phone so large");
            pass = false;
        }

        if (clientRequest.email() == null) {
            stack.add("Email is null");
            pass = false;
        } else if (clientRequest.email().matches("^(.+)@(\\\\S+)$")) {
            stack.add("Email invalid");
            pass = false;
        } else if (clientRequest.email().length() > 100) {
            stack.add("Email so large");
            pass = false;
        }

        return pass;
    }

    @Override
    public List<String> errorStack() {
        return stack;
    }
}
