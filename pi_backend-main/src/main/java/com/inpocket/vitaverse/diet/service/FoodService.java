package com.inpocket.vitaverse.diet.service;

import com.inpocket.vitaverse.diet.entity.Food;

import java.util.List;

public interface FoodService {
    Food createFood(Food food);

    Food getFoodById(long foodId);

    List<Food> getAllFoods();

    Food updateFood(long foodId, Food food);

    void deleteFood(long foodId);
    Food getFoodByName(String foodName);
}