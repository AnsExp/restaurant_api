package com.restaurant.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.requests.ClientRequest;
import com.restaurant.responses.ClientResponse;
import com.restaurant.services.ClientService;
import com.restaurant.validators.ClientRequestValidator;

import lombok.AllArgsConstructor;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("clients")
public class ClientController {

    private final ClientService clientService;
    private final ClientRequestValidator clientRequestValidator;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody(required = false) ClientRequest clientRequest) {
        if (clientRequestValidator.test(clientRequest)) {
            ClientResponse response = clientService.save(clientRequest);
            return ResponseEntity.ok().body(response);
        }
        return ResponseEntity.badRequest().body(clientRequestValidator.errorStack());
    }

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(required = false, name = "page", defaultValue = "-1") int page,
                                  @RequestParam(required = false, name = "order_by") String orderBy) {

        List<ClientResponse> response;

        if (page == -1 && orderBy == null)
            response = clientService.list();
        else if (page == -1)
            response = clientService.list(orderBy);
        else if (orderBy == null)
            response = clientService.list(page);
        else
            response = clientService.list(page, orderBy);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> find(@PathVariable long id) {
        var list = clientService.find(id);
        return ResponseEntity.ok().body(list);
    }
}
