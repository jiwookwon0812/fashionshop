package zerobase.fashionshopapi.service.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import zerobase.fashionshopapi.domain.Product;
import zerobase.fashionshopapi.dto.ProductDto;

@Mapper(componentModel = "spring") // Spring의 빈으로 등록되도록 지시
public interface ProductMapper {

    @Mapping(source = "productName", target = "productName") // DTO의 productName을 엔티티의 productName 필드로 매핑
    @Mapping(source = "price", target = "price") // DTO의 price를 엔티티의 price 필드로 매핑
    @Mapping(source = "category", target = "category") // DTO의 category를 엔티티의 category 필드로 매핑
    @Mapping(source = "description", target = "description") // DTO의 description을 엔티티의 description 필드로 매핑
    @Mapping(source = "stock", target = "stock") // DTO의 stock을 엔티티의 stock 필드로 매핑
    @Mapping(source = "size", target = "size") // DTO의 size를 엔티티의 size 필드로 매핑
    @Mapping(target = "store", ignore = true) // store는 별도로 수동 설정하므로 무시
    Product toEntity(ProductDto productDto);

    @Mapping(source = "store.storeName", target = "storeName") // 엔티티의 store의 storeName을 DTO의 storeName 필드로 매핑
    @Mapping(source = "productName", target = "productName") // 엔티티의 productName을 DTO의 productName 필드로 매핑
    @Mapping(source = "price", target = "price") // 엔티티의 price를 DTO의 price 필드로 매핑
    @Mapping(source = "category", target = "category") // 엔티티의 category를 DTO의 category 필드로 매핑
    @Mapping(source = "description", target = "description") // 엔티티의 description을 DTO의 description 필드로 매핑
    @Mapping(source = "stock", target = "stock") // 엔티티의 stock을 DTO의 stock 필드로 매핑
    @Mapping(source = "size", target = "size") // 엔티티의 size를 DTO의 size 필드로 매핑
    ProductDto toDto(Product product);

    @Mapping(target = "store", ignore = true) // store 수동 설정
    void updateProductFromDto(ProductDto productDto, @MappingTarget Product product);

}
