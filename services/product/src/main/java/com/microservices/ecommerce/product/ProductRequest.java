package com.microservices.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
         Long id,
         @NotNull(message = "Product name is required")
         String name,
         @NotNull(message = "Product description is required")
         String description,
         @Positive(message = "Product availableQty should be positive")
         double availableQty,
         @Positive(message = "Product price should be positive")
         BigDecimal price,
         @NotNull(message = "Product categoryId is required")
         Long CategoryId
) {
}
