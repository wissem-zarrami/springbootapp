package com.inpocket.vitaverse.store.repository;
import com.inpocket.vitaverse.store.entity.ProductMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMarkRepository extends JpaRepository<ProductMark, Long>{
}
