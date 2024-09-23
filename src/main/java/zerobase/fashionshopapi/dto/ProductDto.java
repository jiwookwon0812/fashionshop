package zerobase.fashionshopapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zerobase.fashionshopapi.domain.constant.ProductCategory;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @NotBlank
    private String productName;

    @NotNull
    @Min(1)
    private Integer price;

    @NotNull
    private ProductCategory category;

    private String description;

    @NotNull
    @Min(0)
    private Integer stock;

    private String size;

}
