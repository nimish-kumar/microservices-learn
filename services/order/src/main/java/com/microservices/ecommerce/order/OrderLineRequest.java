package com.microservices.ecommerce.order;

public record OrderLineRequest(
        Long id,
        Long orderId,
        Long productId,
        double quantity) {
}
