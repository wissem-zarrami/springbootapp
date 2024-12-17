package com.inpocket.vitaverse.store.repository;
import com.inpocket.vitaverse.store.entity.PhysicalStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhysicalStoreRepository extends JpaRepository<PhysicalStore, Long>{
}
