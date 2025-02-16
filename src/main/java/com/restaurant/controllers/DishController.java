package com.restaurant.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restaurant.requests.DishRequest;
import com.restaurant.responses.DishResponse;
import com.restaurant.services.DishService;
import com.restaurant.validators.DishRequestValidator;

import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("dishes")
public class DishController {

    private final DishService dishService;
    private final DishRequestValidator dishRequestValidator;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody DishRequest dishRequest) {
        if (dishRequestValidator.test(dishRequest)) {
            DishResponse response = dishService.save(dishRequest);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(Map.of("Error stack", dishRequestValidator.errorStack()));
    }

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(required = false, name = "page", defaultValue = "-1") int page,
                                  @RequestParam(required = false, name = "order_by") String orderBy) {

        List<DishResponse> response;

        if (page == -1 && orderBy == null)
            response = dishService.list();
        else if (orderBy == null)
            response = dishService.list(page);
        else if (page == -1)
            response = dishService.list(orderBy);
        else
            response = dishService.list(page, orderBy);

        return ResponseEntity.ok(response);
    }

    @GetMapping("count")
    public ResponseEntity<?> count() {
        long response = dishService.count();
        return ResponseEntity.ok(Map.of("count", response));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> find(@PathVariable long id) {
        DishResponse response = dishService.find(id);
        return ResponseEntity.ok(response);
    }
}
