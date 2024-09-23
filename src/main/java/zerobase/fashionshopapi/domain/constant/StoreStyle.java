package zerobase.fashionshopapi.domain.constant;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StoreStyle {
    CASUAL,
    STREET,
    VINTAGE,
    SPORTS,
    FEMININE;

    @JsonCreator
    public static StoreStyle fromString(String value) {
        return StoreStyle.valueOf(value.toUpperCase());
    }
}
