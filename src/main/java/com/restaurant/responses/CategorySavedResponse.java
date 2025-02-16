package com.restaurant.responses;

import lombok.Builder;

@Builder
public record CategorySavedResponse(long id, String name) {
}
