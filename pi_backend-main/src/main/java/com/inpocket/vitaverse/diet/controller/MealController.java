package com.inpocket.vitaverse.diet.controller;

import com.inpocket.vitaverse.diet.entity.Food;
import com.inpocket.vitaverse.diet.entity.Image;
import com.inpocket.vitaverse.diet.entity.Meal;
import com.inpocket.vitaverse.diet.repository.MealRepository;
import com.inpocket.vitaverse.diet.service.DietCloudinaryService;
import com.inpocket.vitaverse.diet.service.ImageService;
import com.inpocket.vitaverse.diet.service.MealService;
import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.user.service.DefaultUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/meals")
public class MealController {

    private final MealService mealService;
    private final DietCloudinaryService cloudinaryService;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    ImageService imageService;


    @Autowired
    public MealController(MealService mealService, DietCloudinaryService cloudinaryService, DefaultUserService userService) {
        this.mealService = mealService;
        this.cloudinaryService = cloudinaryService;
        this.userService = userService;
    }
    @GetMapping("/list")
    public ResponseEntity<List<Image>> list(){
        List<Image> list = imageService.list();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value ="/uploadim" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<String> upload(@RequestParam MultipartFile multipartFile) throws IOException {
        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        if (bi == null) {
            return new ResponseEntity<>("Image non valide!", HttpStatus.BAD_REQUEST);
        }
        Map result = cloudinaryService.upload(multipartFile);
        Image image = new Image((String) result.get("original_filename"),
                (String) result.get("url"),
                (String) result.get("public_id"));
        imageService.save(image);
        return new ResponseEntity<>("image ajoutée avec succès ! ", HttpStatus.OK);
    }


    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Meal createMeal(@RequestBody Meal meal) {
        return mealService.saveMeal(meal);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<String> upload(@RequestParam MultipartFile multipartFile, @RequestParam int mealId) throws IOException {
        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        if (bi == null) {
            return new ResponseEntity<>("Image non valide!", HttpStatus.BAD_REQUEST);
        }
        Map result = cloudinaryService.upload(multipartFile);
        String imageUrl = (String) result.get("url"); // Get the URL of the uploaded image

        // Update the meal's imageUrl
        Meal meal = mealService.findMealById(mealId);
        if (meal != null) {
            meal.setMealImageUrl(imageUrl);
            mealService.saveMeal(meal);

            // Create and associate the image with the meal
            Image image = new Image((String) result.get("original_filename"), imageUrl, (String) result.get("public_id"));
            image.setMeal(meal);
            meal.getImages().add(image);
            imageService.save(image);

            return new ResponseEntity<>("Image ajoutée avec succès ! ", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Meal not found!", HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping(value = "/uploade", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<String> upload(@RequestParam("multipartFile") MultipartFile multipartFile,
                                         @RequestParam("mealName") String mealName,
                                         @RequestParam("totalCals") int totalCals,
                                         @RequestParam("totalCarbs") int totalCarbs,
                                         @RequestParam("totalProts") int totalProts) throws IOException {
        // Create a new Meal entity
        Meal meal = new Meal();
        meal.setMealName(mealName);
        meal.setTotalCals(totalCals);
        meal.setTotalCarbs(totalCarbs);
        meal.setTotalprots(totalProts);

        // Upload image
        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        if (bi == null) {
            return new ResponseEntity<>("Image non valide!", HttpStatus.BAD_REQUEST);
        }
        Map result = cloudinaryService.upload(multipartFile);
        String imageUrl = (String) result.get("url");

        // Set the image URL for the meal
        meal.setMealImageUrl(imageUrl);

        // Save the meal entity
        Meal savedMeal = mealService.saveMeal(meal);

        // Create and associate the image with the meal
        Image image = new Image((String) result.get("original_filename"), imageUrl, (String) result.get("public_id"));
        image.setMeal(savedMeal);
        savedMeal.getImages().add(image);
        imageService.save(image);

        return new ResponseEntity<>("Meal avec image ajoutée avec succès !", HttpStatus.CREATED);
    }

    @PostMapping("/{mealId}/addFood")
    public ResponseEntity<Meal> addFoodToMeal(@PathVariable long mealId, @RequestBody Food food) {
        try {
            Meal updatedMeal = mealService.addFoodToMeal(mealId, food);
            return ResponseEntity.ok(updatedMeal);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/{mealId}/associateFood/{foodId}")
    public ResponseEntity<Void> associateFoodWithMeal(@PathVariable long mealId, @PathVariable long foodId) {
        try {
            mealService.associateFoodWithMeal(mealId, foodId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{mealId}/foods")
    public ResponseEntity<List<Food>> getAllFoodsForMeal(@PathVariable long mealId) {
        try {
            List<Food> foods = mealService.getAllFoodsForMeal(mealId);
            return ResponseEntity.ok(foods);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Meal updateMeal(@RequestBody Meal meal) {
        return mealService.saveMeal(meal);
    }

    @GetMapping("/all")
    public List<Meal> getAllMeals() {
        return mealService.getAllMeals();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meal> getMealById(@PathVariable Long id) {
        Meal meal = mealService.getMealById(id);
        return meal != null ? ResponseEntity.ok(meal) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMeal(@PathVariable("id") long id) {
        mealService.deleteMealById(id);
    }

    //just to try
    private final DefaultUserService userService;

    @GetMapping("/allUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable long userId) {
        User user = userService.getUserById(userId);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/deleteUser/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("userId") long userId) {
        userService.deleteUser(userId);
    }

    @PostMapping("/{mealId}/associateUser/{userId}")
    public ResponseEntity<Void> associateMealWithUser(@PathVariable long mealId, @PathVariable long userId) {
        try {
            mealService.associateMealWithUser(mealId, userId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/user/{userId}/meals")
    public ResponseEntity<List<Meal>> getUserMeals(@PathVariable long userId) {
        try {
            List<Meal> userMeals = mealService.getUserMeals(userId);
            return ResponseEntity.ok(userMeals);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/user/{userId}/totalProtein")
    public ResponseEntity<Integer> calculateTotalProteinForUser(@PathVariable long userId) {
        try {
            int totalProtein = mealService.calculateTotalProteinForUser(userId);
            return ResponseEntity.ok(totalProtein);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{mealId}/foods/{foodId}")
    public ResponseEntity<Void> removeFoodFromMeal(@PathVariable long mealId, @PathVariable long foodId) {
        try {
            mealService.removeFoodFromMeal(mealId, foodId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{mealId}/disassociateUser/{userId}")
    public ResponseEntity<Void> disassociateMealFromUser(@PathVariable long mealId, @PathVariable long userId) {
        try {
            mealService.disassociateMealFromUser(mealId, userId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
