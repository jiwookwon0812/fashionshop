package zerobase.fashionshopapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.fashionshopapi.domain.Store;
import zerobase.fashionshopapi.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {
    Optional<Store> findByStorenameAndUser(String storeName, User user);

    List<Store> findByStorename(String storeName);
}
