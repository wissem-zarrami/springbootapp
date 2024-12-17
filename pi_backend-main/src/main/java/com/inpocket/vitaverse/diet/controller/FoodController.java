package com.inpocket.vitaverse.diet.controller;

import com.inpocket.vitaverse.diet.entity.Food;
import com.inpocket.vitaverse.diet.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/foods")
public class FoodController {

    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Food createFood(@RequestBody Food food) {
        return foodService.createFood(food);
    }

    @PutMapping("/update/{foodId}")
    public ResponseEntity<Food> updateFood(@PathVariable long foodId, @RequestBody Food food) {
        Food updatedFood = foodService.updateFood(foodId, food);
        if (updatedFood != null) {
            return ResponseEntity.ok(updatedFood);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public List<Food> getAllFoods() {
        return foodService.getAllFoods();
    }

    @GetMapping("/{foodId}")
    public ResponseEntity<Food> getFoodById(@PathVariable long foodId) {
        Food food = foodService.getFoodById(foodId);
        return food != null ? ResponseEntity.ok(food) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{foodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFood(@PathVariable("foodId") long foodId) {
        foodService.deleteFood(foodId);
    }

    @GetMapping("/byname")
    public ResponseEntity<Food> getFoodByName(@RequestParam String foodName) {
        Food food = foodService.getFoodByName(foodName);
        return food != null ? ResponseEntity.ok(food) : ResponseEntity.notFound().build();
    }

}
