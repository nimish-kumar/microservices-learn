package com.microservices.ecommerce.order;

import com.microservices.ecommerce.product.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Long id,
        String reference,
        @Positive(message = "Order amount should be positive")
        BigDecimal amount,
        @NotNull(message = "Payment method cannot be null")
        PaymentMethod paymentMethod,
        @NotNull(message = "Customer id cannot be null")
        @NotEmpty(message = "Customer id cannot be empty")
        @NotBlank(message = "Customer id cannot be blank")
        String customerId,

        @NotEmpty(message = "You should atleast purchase one product")
        List<PurchaseRequest> products
) {
}
