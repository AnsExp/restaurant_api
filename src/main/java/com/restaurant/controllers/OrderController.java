package com.restaurant.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.requests.OrderRequest;
import com.restaurant.responses.OrderResponse;
import com.restaurant.services.OrderService;
import com.restaurant.validators.OrderRequestValidator;

import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderRequestValidator orderRequestValidator;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody(required = false) OrderRequest orderRequest) {
        if (orderRequestValidator.test(orderRequest)) {
            OrderResponse response = orderService.save(orderRequest);
            return ResponseEntity.ok().body(response);
        }
        return ResponseEntity.badRequest().body(Map.of("Error stack", orderRequestValidator.errorStack()));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> modify(@PathVariable long id, @RequestBody(required = false) OrderRequest orderRequest) {
        if (!orderService.exits(id))
            return ResponseEntity.badRequest().body(Map.of("message", "Order don't exits."));
        if (!orderRequestValidator.test(orderRequest))
            return ResponseEntity.badRequest().body(Map.of("Error stack", orderRequestValidator.errorStack()));
        return ResponseEntity.ok().body(orderService.modify(id, orderRequest));
    }

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(required = false, name = "page", defaultValue = "-1") int page,
                                  @RequestParam(required = false, name = "order_by") String orderBy) {
        List<OrderResponse> response;

        if (page == -1 && orderBy == null)
            response = orderService.list();
        else if (page == -1)
            response = orderService.list(orderBy);
        else if (orderBy == null)
            response = orderService.list(page);
        else
            response = orderService.list(page, orderBy);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> find(@PathVariable long id) {
        return ResponseEntity.ok().body(orderService.find(id));
    }
}
