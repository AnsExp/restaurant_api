package com.restaurant.responses;

import lombok.Builder;

@Builder
public record ResourceSavedResponse(long id, String path) {
}
