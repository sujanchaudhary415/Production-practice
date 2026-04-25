package com.productionPractice1.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    @NotBlank(message = "category must not be blank")
    @Size(min=5,max = 30,message = "category name must be between 5 and 30")
    private String categoryName;
}
