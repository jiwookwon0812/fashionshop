package zerobase.fashionshopapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zerobase.fashionshopapi.domain.constant.StoreStyle;


public class StoreDto {

    // 상점 등록 시
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class registerDto {
        @NotBlank
        private String storeName;

        @NotNull
        private StoreStyle storeStyle;
    }

    // 상점 업데이트 할 때에는 null 허용
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class updateDto {

        private String storeName;

        private StoreStyle storeStyle;
    }

}
