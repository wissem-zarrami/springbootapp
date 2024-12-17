package com.inpocket.vitaverse.store.repository;

import com.inpocket.vitaverse.store.entity.ShoppingCart;
import com.inpocket.vitaverse.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>{
    Optional<ShoppingCart> findByUser(User user);
}
