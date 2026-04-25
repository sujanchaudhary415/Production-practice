package com.productionPractice1.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    @NotBlank(message = "product must not be blank")
    private String productName;

    @NotBlank(message="image must not be blank")
    private String image;

    private String description;

    @NotNull(message ="price must not be blank")
    @Positive
    private double price;
}
