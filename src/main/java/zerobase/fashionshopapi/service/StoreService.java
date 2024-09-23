package zerobase.fashionshopapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import zerobase.fashionshopapi.domain.Store;
import zerobase.fashionshopapi.domain.User;
import zerobase.fashionshopapi.domain.constant.StoreStyle;
import zerobase.fashionshopapi.dto.StoreDto;
import zerobase.fashionshopapi.repository.StoreRepository;
import zerobase.fashionshopapi.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    // 상점 등록
    public void registerStore(StoreDto storeDto, Authentication authentication) {
        String email = authentication.getName(); // 이메일 가져오기
        // generateToken 메서드에서 토큰 빌드 시 setSubject(email) 을 통해
        // 토큰의 주체(Principal)을 이메일로 설정했기 때문에 이메일 반환

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User is not found"));

        // 중복 상점 등록 X
        if (storeRepository.findByStorenameAndUser(storeDto.getStoreName(), user).isPresent()) {
            throw new RuntimeException("This store is already existed");
        }

        Store store = new Store(user, storeDto.getStoreName(), storeDto.getStoreStyle());
        storeRepository.save(store);
    }

    // 상점 수정
    public void updateStore(StoreDto storeDto, String storeName, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User is not found"));

        Store store = storeRepository.findByStorenameAndUser(storeName, user)
                .orElseThrow(() -> new RuntimeException("Store not found"));


        // 3. 업데이트
        if (!storeDto.getStoreName().isBlank() && storeDto.getStoreStyle() != null) {
            // 둘 다 null이 아닌 경우
            store.updateStoreName(storeDto.getStoreName());
            store.updateStoreStyle(storeDto.getStoreStyle());
        } else if (!storeDto.getStoreName().isBlank()) {
            // 이름만 null이 아닌 경우
            store.updateStoreName(storeDto.getStoreName());
        } else if (storeDto.getStoreStyle() != null) {
            // 스타일만 null이 아닌 경우
            store.updateStoreStyle(storeDto.getStoreStyle());
        }
        storeRepository.save(store);
    }

    // 상점 삭제
    public void deleteStore(String storeName, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User is not found"));

        Store store = storeRepository.findByStorenameAndUser(storeName, user)
                .orElseThrow(() -> new RuntimeException("Store is not found"));

        storeRepository.delete(store);
    }

    // 상점 조회
    public List<StoreDto> searchStores(String storeName, StoreStyle storeStyle) {
        if (storeName == null && storeStyle == null) {
            List<Store> allStores = storeRepository.findAll();
            List<StoreDto> allDtoList = allStores.stream()
                    .map(e -> new StoreDto(e.getStorename(), e.getStorestyle()))
                    .toList();
            return allDtoList;
        } else if (storeName != null && storeStyle == null){
            List<Store> stores = storeRepository.findByStorename(storeName);
            List<StoreDto> storeList = stores.stream()
                    .map(e -> new StoreDto(e.getStorename(), e.getStorestyle()))
                    .toList();
            return storeList;
        } else if (storeName == null && storeStyle != null) {
            List<Store> stores = storeRepository.findByStoreStyle(storeStyle);
        }
        return null;
    }

}
