package com.inpocket.vitaverse.diet.repository;
import com.inpocket.vitaverse.diet.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long>{
    Food findByFoodName(String foodName);
}
