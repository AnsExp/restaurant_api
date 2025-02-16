package com.restaurant.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.requests.CategoryRequest;
import com.restaurant.services.CategoryService;
import com.restaurant.validators.CategoryRequestValidator;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryRequestValidator categoryRequestValidator;

    @GetMapping
    public ResponseEntity<?> listCategories() {
        return ResponseEntity.ok().body(categoryService.listCategories());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findCategory(@PathVariable long id) {
        return ResponseEntity.ok().body(categoryService.findCategory(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> modify(@PathVariable long id,
            @RequestBody(required = false) CategoryRequest categoryRequest) {
        if (!categoryRequestValidator.test(categoryRequest))
            return ResponseEntity.badRequest().body(Map.of("Error stack", categoryRequestValidator.errorStack()));
        if (!categoryService.exits(id))
            return ResponseEntity.badRequest().body(Map.of("message", "Category don't exits."));
        return ResponseEntity.ok().body(categoryService.modify(id, categoryRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> remove(@PathVariable long id) {
        categoryService.remove(id);
        return ResponseEntity.ok().body(Map.of("message", "Category removed."));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody(required = false) CategoryRequest categoryRequest) {
        if (categoryRequestValidator.test(categoryRequest))
            return ResponseEntity.ok().body(categoryService.save(categoryRequest));
        return ResponseEntity.badRequest().body(Map.of("Error stack", categoryRequestValidator.errorStack()));
    }
}
