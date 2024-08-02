package com.microservices.ecommerce.order;

public record OrderLineResponse(
        Long id,
        double quantity
) {
}
