package com.restaurant.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restaurant.requests.InvoiceRequest;
import com.restaurant.responses.InvoiceResponse;
import com.restaurant.services.InvoiceService;
import com.restaurant.validators.InvoiceRequestValidator;

import lombok.AllArgsConstructor;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceRequestValidator invoiceRequestValidator;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody(required = false) InvoiceRequest invoiceRequest) {
        if (invoiceRequestValidator.test(invoiceRequest)) {
            InvoiceResponse response = invoiceService.save(invoiceRequest);
            return ResponseEntity.ok().body(response);
        }
        return ResponseEntity.badRequest().body(invoiceRequestValidator.errorStack());
    }

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(required = false, name = "page", defaultValue = "-1") int page,
                                  @RequestParam(required = false, name = "order_by") String orderBy) {
        List<InvoiceResponse> response;

        if (page == -1 && orderBy == null)
            response = invoiceService.list();
        else if (page == -1)
            response = invoiceService.list(orderBy);
        else if (orderBy == null)
            response = invoiceService.list(page);
        else
            response = invoiceService.list(page, orderBy);

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> find(@PathVariable long id) {
        InvoiceResponse response = invoiceService.find(id);
        return ResponseEntity.ok().body(response);
    }
}
