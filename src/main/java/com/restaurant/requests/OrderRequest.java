package com.restaurant.requests;

import java.util.List;

public record OrderRequest(int table, List<OrderItemRequest> dishes, String message) {
    public record OrderItemRequest(long dish, int quantity) {
    }
}
