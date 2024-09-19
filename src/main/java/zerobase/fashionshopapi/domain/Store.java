package zerobase.fashionshopapi.domain;

import jakarta.persistence.*;
import lombok.*;
import zerobase.fashionshopapi.domain.constant.StoreStyle;


@Entity(name = "Store")
@Getter
@ToString
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    @Column(nullable = false)
    private String storename;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StoreStyle storestyle;

    public Store(User user, String storename, StoreStyle storestyle) {
        this.user = user;
        this.storename = storename;
        this.storestyle = storestyle;
    }

    public void updateStoreName(String storeName) {
        this.storename = storeName;
    }

    public void updateStoreStyle(StoreStyle storeStyle) {
        this.storestyle = storeStyle;
    }
}
