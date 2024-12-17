package com.inpocket.vitaverse.diet.service;

import com.inpocket.vitaverse.diet.entity.Food;
import com.inpocket.vitaverse.diet.entity.Meal;
import com.inpocket.vitaverse.diet.repository.FoodRepository;
import com.inpocket.vitaverse.diet.repository.MealRepository;
import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class MealServiceImp  implements MealService  {



    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private FoodRepository foodRepository;
    private final DietCloudinaryService cloudinaryService;

    @Autowired
    public MealServiceImp(DietCloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public Meal saveMeal(Meal meal) {
        return mealRepository.save(meal);
    }

    @Override
    public Meal getMealById(long id) {
        return mealRepository.findById(id).orElse(null);
    }

    @Override
    public List<Meal> getAllMeals() {
        List<Meal> meals = mealRepository.findAll(); // Fetch all meals from the repository

        for (Meal meal : meals) { // Iterate over each meal to recalculate totals and save
            recalculateMealTotals(meal);
            mealRepository.save(meal);
        }

        return meals; // Return the list of meals
    }


    public Meal findMealById(long mealId) {
        Optional<Meal> optionalMeal = mealRepository.findById(mealId);
        return optionalMeal.orElse(null);
    }



    public void setMealImageUrl(String mealName, MultipartFile multipartFile) {
        try {
            String mealImageUrl = cloudinaryService.upload(multipartFile).toString(); // Upload the image and get the URL

            // Find the meal by its name
            Meal meal = mealRepository.findByMealName(mealName);
            if (meal != null) {
                meal.setMealImageUrl(mealImageUrl); // Set the image URL for the meal
                mealRepository.save(meal); // Save the meal with the updated image URL
            }
        } catch (IOException e) {
            // Handle the IOException (e.g., log the error, perform fallback action, etc.)
            e.printStackTrace(); // Example of handling the exception
        }
    }




    @Override
    public void deleteMealById(long id) {
        mealRepository.deleteById(id);
    }

    @Override
    public Meal updateMeal(long id, Meal updatedMeal) {
        Optional<Meal> existingMealOptional = mealRepository.findById(id);
        if (existingMealOptional.isPresent()) {
            Meal existingMeal = existingMealOptional.get();
            existingMeal.setMealName(updatedMeal.getMealName());
            existingMeal.setTotalCals(updatedMeal.getTotalCals());
            existingMeal.setTotalCarbs(updatedMeal.getTotalCarbs());
            existingMeal.setTotalprots(updatedMeal.getTotalprots());
            existingMeal.setFoods(updatedMeal.getFoods());
            existingMeal.setDiet(updatedMeal.getDiet());
            return mealRepository.save(existingMeal);
        }
        return null; // or throw an exception if you prefer
    }

    @Override
    public Meal addFoodToMeal(long mealId, Food food) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Meal not found"));

        // Ensure the foods collection is initialized
        if (meal.getFoods() == null) {
            meal.setFoods(new HashSet<>());
        }

        meal.getFoods().add(food); // Add the food to the meal
        recalculateMealTotals(meal); // Recalculate meal totals

        // Save the updated meal to the repository
        meal = mealRepository.save(meal);

        return meal; // Return the updated meal
    }

    @Override
    public void associateFoodWithMeal(long mealId, long foodId) {
        Optional<Meal> mealOptional = mealRepository.findById(mealId);
        Optional<Food> foodOptional = foodRepository.findById(foodId);

        if (mealOptional.isPresent() && foodOptional.isPresent()) {
            Meal meal = mealOptional.get();
            Food food = foodOptional.get();

            // Associate food with meal
            meal.getFoods().add(food);
            food.getMeals().add(meal);

            // Save changes to update associations
            mealRepository.save(meal);
            foodRepository.save(food);


            recalculateMealTotals(meal);
            meal = mealRepository.save(meal);

        } else {
            // Handle not found scenario
            throw new IllegalArgumentException("Meal or Food not found");
        }
    }

    private void recalculateMealTotals(Meal meal) {
        int totalCals = 0;
        int totalCarbs = 0;
        int totalProts = 0;

        // Retrieve all foods associated with the meal from the database
        List<Food> foods = getAllFoodsForMeal(meal.getMealId());

        // Iterate through all retrieved foods and add their values
        for (Food food : foods) {
            totalCals += food.getCals();
            totalCarbs += food.getCarbs();
            totalProts += food.getProts();
        }

        // Set the calculated totals to the meal
        meal.setTotalCals(totalCals);
        meal.setTotalCarbs(totalCarbs);
        meal.setTotalprots(totalProts);
    }

    public List<Food> getAllFoodsForMeal(long mealId) {
        Optional<Meal> mealOptional = mealRepository.findById(mealId);
        if (mealOptional.isPresent()) {
            Meal meal = mealOptional.get();
            // Convert Set<Food> to List<Food>
            return new ArrayList<>(meal.getFoods());
        } else {
            throw new IllegalArgumentException("Meal not found");
        }
    }
    @Autowired
    private UserRepository userRepository; // Assuming you have a UserRepository

    // Other methods...

    @Override
    public void associateMealWithUser(long mealId, long userId) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Meal not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Associate the meal with the user
        meal.getUsers().add(user);

        // Save the updated meal to persist the association
        mealRepository.save(meal);
    }
    @Override
    public void disassociateMealFromUser(long mealId, long userId) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Meal not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Remove the association between the meal and the user
        meal.getUsers().remove(user);

        // Save the updated meal to persist the disassociation
        mealRepository.save(meal);
    }

    @Override
    public List<Meal> getUserMeals(long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Assuming you have a method in User entity to get meals associated with the user
            Set<Meal> mealSet = user.getMeals();
            // Convert the Set to a List
            List<Meal> mealList = new ArrayList<>(mealSet);
            return mealList;
        } else {
            // Handle the case when the user is not found
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }
    @Override
    public int calculateTotalProteinForUser(long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Set<Meal> userMeals = user.getMeals();
            int totalProtein = 0;

            // Iterate through each meal associated with the user
            for (Meal meal : userMeals) {
                totalProtein += meal.getTotalprots();
            }
            return totalProtein;
        } else {
            // Handle the case when the user is not found
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }
    @Override
    public void removeFoodFromMeal(long mealId, long foodId) {
        Optional<Meal> mealOptional = mealRepository.findById(mealId);
        Optional<Food> foodOptional = foodRepository.findById(foodId);

        if (mealOptional.isPresent() && foodOptional.isPresent()) {
            Meal meal = mealOptional.get();
            Food food = foodOptional.get();

            // Remove food from meal's list of foods
            meal.getFoods().remove(food);

            // Remove meal from food's list of meals
            food.getMeals().remove(meal);

            // Save changes to update associations
            mealRepository.save(meal);
            foodRepository.save(food);

            recalculateMealTotals(meal);
            meal = mealRepository.save(meal);
        } else {
            // Handle not found scenario
            throw new IllegalArgumentException("Meal or Food not found");
        }
    }
}
