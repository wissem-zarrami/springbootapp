package com.inpocket.vitaverse.store.repository;
import com.inpocket.vitaverse.store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    Product getOneByName(String name);
    List<Product> findByCreatedAtAfter(LocalDateTime cutoffDateTime);
    @Query("SELECT p FROM Product p " +
            "WHERE (:name IS NULL OR p.name LIKE %:name%) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "AND (:markName IS NULL OR p.mark.name LIKE %:markName%) " +
            "AND (:userName IS NULL OR p.seller.username LIKE %:userName%)")
    List<Product> searchProducts(@Param("name") String name,
                                 @Param("minPrice") float minPrice,
                                 @Param("maxPrice") float maxPrice,
                                 @Param("markName") String markName,
                                 @Param("userName") String userName);

    @Query("SELECT p FROM Product p " +
            "WHERE (:name IS NULL OR p.name LIKE %:name%) ")
    List<Product> searchProducts(@Param("name") String name);
}
