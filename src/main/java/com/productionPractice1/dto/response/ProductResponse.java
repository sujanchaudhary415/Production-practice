package com.productionPractice1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private String productName;
    private String image;
    private String description;
    private int quantity;
    private double price;
    private double specialPrice;
}
