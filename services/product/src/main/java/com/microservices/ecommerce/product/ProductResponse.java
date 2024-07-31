package com.microservices.ecommerce.product;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String description,
        double availableQty,
        BigDecimal price,
        Long categoryId,
        String categoryName,
        String categoryDescription
) {
}
