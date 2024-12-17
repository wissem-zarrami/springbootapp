package com.inpocket.vitaverse.store.repository;
import com.inpocket.vitaverse.store.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{
    List<Payment> findByPaymentDateAfter(LocalDateTime cutoffDateTime);
    Optional<Payment> findByShipmentUserUserUserId(Long userId);
    Optional<Payment> findByShoppingCartUserUserId(Long userId);

}
