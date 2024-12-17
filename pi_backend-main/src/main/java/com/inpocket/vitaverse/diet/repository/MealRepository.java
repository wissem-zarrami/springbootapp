package com.inpocket.vitaverse.diet.repository;

import com.inpocket.vitaverse.diet.entity.Food;
import com.inpocket.vitaverse.diet.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository <Meal, Long>{
    Meal findByMealName(String mealName);
}
