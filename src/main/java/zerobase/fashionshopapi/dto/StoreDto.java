package zerobase.fashionshopapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zerobase.fashionshopapi.domain.constant.StoreStyle;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {

        @NotBlank
        private String storeName;

        @NotNull
        private StoreStyle storeStyle;

}
