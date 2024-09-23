package zerobase.fashionshopapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import zerobase.fashionshopapi.domain.Product;
import zerobase.fashionshopapi.domain.Store;
import zerobase.fashionshopapi.domain.User;
import zerobase.fashionshopapi.dto.ProductDto;
import zerobase.fashionshopapi.repository.ProductRepository;
import zerobase.fashionshopapi.repository.StoreRepository;
import zerobase.fashionshopapi.repository.UserRepository;
import zerobase.fashionshopapi.service.mapstruct.ProductMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ProductMapper productMapper;

    // 유저 찾는 메서드
    private User findUser(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // 상점 찾는 메서드
    private Store findStore(User user, String storeName) {
        return storeRepository.findByStorenameAndUser(storeName, user)
                .orElseThrow(() -> new RuntimeException("Store not found"));
    }

    // 상품 등록
    public void registerProduct(String storeName, ProductDto productDto, Authentication authentication) {
        Store store = findStore(findUser(authentication), storeName);
        Product product = new Product(store, productDto.getProductName(),
                productDto.getPrice(), productDto.getCategory(), productDto.getDescription(),
                productDto.getStock(), productDto.getSize());
        productRepository.save(product);
    }

    // 상품 수정
    public void updateProduct(String storeName, String productName, ProductDto productDto, Authentication authentication) {
        Store store = findStore(findUser(authentication), storeName);
        Product product = productRepository.findByStoreAndProductName(store, productName)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductDto existingDto = productMapper.toDto(product);
        // 기존 상품 정보 가져오기

        if (productDto.getProductName() == null || productDto.getProductName().isEmpty()) {
            productDto.setProductName(existingDto.getProductName());
        }
        if (productDto.getPrice() == null) {
            productDto.setPrice(existingDto.getPrice());
        }
        if (productDto.getCategory() == null) {
            productDto.setCategory(existingDto.getCategory());
        }
        if (productDto.getDescription() == null || productDto.getDescription().isEmpty()) {
            productDto.setDescription(existingDto.getDescription());
        }
        if (productDto.getStock() == null) {
            productDto.setStock(existingDto.getStock());
        }
        if (productDto.getSize() == null) {
            productDto.setSize(existingDto.getSize());
        }

        productMapper.updateProductFromDto(productDto, product);
        productRepository.save(product);

    }

    // 상품 삭제
    public void deleteProduct(String storeName, String productName, Authentication authentication) {
        Store store = findStore(findUser(authentication), storeName);
        Product product = productRepository.findByStoreAndProductName(store, productName)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.delete(product);
    }



}
