package com.microservices.ecommerce.product;

import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRequest(
        @NotNull(message = "Product id is mandatory")
        Long productId,

        @NotNull(message = "Quantity is mandatory")
        double quantity
) {
}
