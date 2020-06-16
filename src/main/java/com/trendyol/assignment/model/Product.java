package com.trendyol.assignment.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue()
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    private String name;
    private String description;

    @Digits(integer=5, fraction=2)
    private BigDecimal price;
}
