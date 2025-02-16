package com.restaurant.responses;

import lombok.Builder;

@Builder
public record ResourceFoundResponse(String type, byte[] bytes) {
}
