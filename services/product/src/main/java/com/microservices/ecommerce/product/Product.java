package com.microservices.ecommerce.product;

import com.microservices.ecommerce.category.Category;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double availableQty;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
}
