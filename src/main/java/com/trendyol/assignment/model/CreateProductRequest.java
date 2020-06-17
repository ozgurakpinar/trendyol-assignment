package com.trendyol.assignment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequest {
    private Product product;
    private Long categoryId;
}
