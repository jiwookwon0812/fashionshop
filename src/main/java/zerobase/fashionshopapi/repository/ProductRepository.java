package zerobase.fashionshopapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.fashionshopapi.domain.Product;
import zerobase.fashionshopapi.domain.Store;
import zerobase.fashionshopapi.domain.constant.ProductCategory;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByStoreAndProductName(Store store, String productName);
    List<Product> findByStore(Store store);
    List<Product> findByStoreAndCategory(Store store, ProductCategory productCategory);
}
