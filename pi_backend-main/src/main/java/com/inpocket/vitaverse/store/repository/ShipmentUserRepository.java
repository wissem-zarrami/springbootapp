package com.inpocket.vitaverse.store.repository;

import com.inpocket.vitaverse.store.entity.ShipmentUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipmentUserRepository extends JpaRepository<ShipmentUser, Long> {
    Optional<ShipmentUser> findByIsAvailable(boolean isAvailable);
}
