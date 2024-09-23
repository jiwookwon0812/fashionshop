package zerobase.fashionshopapi.domain.constant;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ProductCategory {
    TOP,
    BOTTOM,
    OUTER,
    DRESS,
    ACCESSORY,
    SHOES,
    BAG;

    @JsonCreator
    public static ProductCategory fromString(String value) {
        return ProductCategory.valueOf(value.toUpperCase());
    }
}
