package com.restaurant.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record InvoiceRequest(@JsonProperty("client_id") long clientId, @JsonProperty("order_id") long orderId) {
}
