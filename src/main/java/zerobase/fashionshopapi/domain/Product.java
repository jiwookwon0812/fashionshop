package zerobase.fashionshopapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import zerobase.fashionshopapi.domain.constant.ProductCategory;

@Entity(name = "Product")
@Getter
@ToString
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;  // 외래키 설정 (Store와의 관계)

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCategory category;

    @Lob
    private String description;

    @Column(nullable = false)
    private int stock;

    @Column
    private String size;

    public Product(Store store, String productName, int price, ProductCategory category, String description, int stock, String size) {
        this.store = store;
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.description = description;
        this.stock = stock;
        this.size = size;
    }

    // 상품 정보 수정
    public void updateProduct(String productName, int price, ProductCategory category, String description, int stock, String size) {
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.description = description;
        this.stock = stock;
        this.size = size;
    }

}
