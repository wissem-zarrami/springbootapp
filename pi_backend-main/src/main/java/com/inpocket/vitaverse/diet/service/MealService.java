package com.inpocket.vitaverse.diet.service;

import com.inpocket.vitaverse.diet.entity.Food;
import com.inpocket.vitaverse.diet.entity.Meal;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface MealService {
    Meal saveMeal(Meal meal);
    Meal getMealById(long id);
    List<Meal> getAllMeals();
    void deleteMealById(long id);
    Meal updateMeal(long id, Meal updatedMeal);
    void setMealImageUrl(String mealName, MultipartFile file);
    void associateFoodWithMeal(long mealId, long foodId);
    List<Food> getAllFoodsForMeal(long mealId);
    Meal findMealById(long mealId);
    Meal addFoodToMeal(long mealId, Food food);
    void associateMealWithUser(long mealId, long userId);
    List<Meal> getUserMeals(long userId);
    int calculateTotalProteinForUser(long userId);
    void removeFoodFromMeal(long mealId, long foodId);

    public void disassociateMealFromUser(long mealId, long userId);
}
