package com.restaurant.responses;

import lombok.Builder;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@Builder
public record OrderResponse(long id, int table, @JsonProperty("date_time") String dateTime, String message,
        List<OrderItemResponse> items, String subtotal, String iva, String total) {
    @Builder
    public record OrderItemResponse(String name, int quantity, String price, String subtotal) {
    }
}
