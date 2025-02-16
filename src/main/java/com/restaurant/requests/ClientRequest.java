package com.restaurant.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ClientRequest(long id, String firstname, String lastname, String phone, String email,
                            @JsonProperty("id_card") String idCard) {
}
