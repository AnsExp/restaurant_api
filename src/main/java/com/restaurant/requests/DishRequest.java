package com.restaurant.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DishRequest(long id, String name, Long price, @JsonProperty("category_id") long categoryId) {
}
