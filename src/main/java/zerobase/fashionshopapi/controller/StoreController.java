package zerobase.fashionshopapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import zerobase.fashionshopapi.domain.constant.StoreStyle;
import zerobase.fashionshopapi.dto.StoreDto;
import zerobase.fashionshopapi.service.StoreService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    // 상점 등록
    @PostMapping("/register")
    public ResponseEntity<?> registerStore(@RequestBody @Valid StoreDto storeDto, Authentication authentication) {
        storeService.registerStore(storeDto, authentication);
        return ResponseEntity.ok("Store is registered successfully");
    }

    // 상점 수정 (본인
    @PutMapping("/update/{storeName}")
    public ResponseEntity<?> updateStore(@RequestBody StoreDto storeDto, @PathVariable String storeName, Authentication authentication) {
        storeService.updateStore(storeDto, storeName, authentication);
        return ResponseEntity.ok("Store is updated successfully");
    }


    // 상점 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteStore(@RequestParam String storeName, Authentication authentication) {
        storeService.deleteStore(storeName, authentication);
        return ResponseEntity.ok("Store is deleted successfully");
    }


    // 상점 조회
    @GetMapping("/{storeStyle}")
    public List<StoreDto> searchStores(@RequestParam(required = false) String storeName,
                                       @PathVariable(required = false) StoreStyle storeStyle) {
        return storeService.searchStores(storeName, storeStyle);
    }

}
