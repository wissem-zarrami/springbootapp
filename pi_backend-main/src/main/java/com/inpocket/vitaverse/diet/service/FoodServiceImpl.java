package com.inpocket.vitaverse.diet.service;

import com.inpocket.vitaverse.diet.entity.Food;
import com.inpocket.vitaverse.diet.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;

    @Autowired
    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public Food createFood(Food food) {
        return foodRepository.save(food);
    }

    @Override
    public Food getFoodById(long foodId) {
        Optional<Food> foodOptional = foodRepository.findById(foodId);
        return foodOptional.orElse(null);
    }

    @Override
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    @Override
    public Food updateFood(long foodId, Food food) {
        food.setFoodId(foodId);
        return foodRepository.save(food);
    }

    @Override
    public void deleteFood(long foodId) {
        foodRepository.deleteById(foodId);
    }
    @Override
    public Food getFoodByName(String foodName) {
        return foodRepository.findByFoodName(foodName);
    }
}
