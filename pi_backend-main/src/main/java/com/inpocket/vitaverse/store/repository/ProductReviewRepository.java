package com.inpocket.vitaverse.store.repository;
import com.inpocket.vitaverse.store.entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long>{
    List<ProductReview> findByProductProductId(long productId);
}
