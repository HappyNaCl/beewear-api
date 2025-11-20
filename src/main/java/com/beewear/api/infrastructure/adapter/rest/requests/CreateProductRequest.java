package com.beewear.api.infrastructure.adapter.rest.requests;

import com.beewear.api.domain.entities.enums.Gender;
import com.beewear.api.domain.entities.enums.ProductCategory;
import com.beewear.api.domain.exceptions.InvalidGenderException;
import com.beewear.api.domain.exceptions.InvalidProductCategoryException;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    @NotBlank(message = "Name can not be empty")
    private String name;

    private String description;

    @NotBlank(message = "Price can not be empty")
    private Double price;


    private Gender gender;
    private ProductCategory productCategory;

    public void setGender(String val) {
        try{
            this.gender = Gender.valueOf(val);
        } catch (Exception e) {
            throw new InvalidGenderException();
        }
    }

    public void setProductCategory(String val) {
        try{
            this.productCategory = ProductCategory.valueOf(val);
        } catch (Exception e) {
            throw new InvalidProductCategoryException();
        }
    }
}
