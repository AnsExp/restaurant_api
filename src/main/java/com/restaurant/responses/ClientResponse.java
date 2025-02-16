package com.restaurant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record ClientResponse(long id, String firstname, String lastname, @JsonProperty("id_card") String idCard, String phone, String email) {
}
