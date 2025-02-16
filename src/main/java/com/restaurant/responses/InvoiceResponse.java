package com.restaurant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record InvoiceResponse(long id, InvoiceResponseClient client, String date, String subtotal, String iva, String total) {
    @Builder
    public record InvoiceResponseClient(String firstname, String lastname, @JsonProperty("id_card") String idCard, String phone, String email) {
    }
}
