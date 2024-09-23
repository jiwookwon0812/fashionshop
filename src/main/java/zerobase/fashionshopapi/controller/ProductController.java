package zerobase.fashionshopapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import zerobase.fashionshopapi.dto.ProductDto;
import zerobase.fashionshopapi.service.ProductService;

@Slf4j
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // 상품 등록
    @PostMapping("/register/{storeName}")
    public ResponseEntity<?> register(@PathVariable String storeName,
                                      @RequestBody @Valid ProductDto productDto, Authentication authentication) {
        productService.registerProduct(storeName, productDto, authentication);
        return ResponseEntity.ok("Product registered successfully");
    }

    // 상품 수정 (업데이트)
    @PutMapping("/update/{storeName}")
    public ResponseEntity<?> updateProduct(@PathVariable String storeName,
                                           @RequestParam String productName,
                                           @RequestBody ProductDto productDto,
                                           Authentication authentication) {
        productService.updateProduct(storeName, productName, productDto, authentication);
        return ResponseEntity.ok("Product updated successfully");
    }

    // 상품 삭제
    @DeleteMapping("/delete/{storeName}/product/{productName}")
    public ResponseEntity<?> deleteProduct(@PathVariable String storeName,
                                           @PathVariable String productName,
                                           Authentication authentication) {
        productService.deleteProduct(storeName, productName, authentication);
        return ResponseEntity.ok("Product deleted successfully");
    }

}
