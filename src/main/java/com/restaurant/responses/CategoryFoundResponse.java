package com.restaurant.responses;

import lombok.Builder;

import java.util.List;

@Builder
public record CategoryFoundResponse(long id, String name, List<DishResponse> dishes) {

    @Builder
    public record DishResponse(long id, String name, String price) {
    }
}
