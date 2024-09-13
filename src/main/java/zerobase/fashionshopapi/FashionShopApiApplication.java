package zerobase.fashionshopapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FashionShopApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FashionShopApiApplication.class, args);
    }

}
