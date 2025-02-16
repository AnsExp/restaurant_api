package com.restaurant.responses;

import lombok.Builder;

@Builder
public record DishResponse(long id, String name, String price, String category) {
}
