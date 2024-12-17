package com.inpocket.vitaverse.workout.controller;



import com.inpocket.vitaverse.diet.entity.Image;
import com.inpocket.vitaverse.diet.service.DietCloudinaryService;
import com.inpocket.vitaverse.diet.service.ImageService;
import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.user.service.DefaultUserService;
import com.inpocket.vitaverse.workout.entity.Exercice;
import com.inpocket.vitaverse.workout.entity.ExerciceType;
import com.inpocket.vitaverse.workout.service.ExerciceService;
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
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/exercices")
public class ExerciceController {

    private final ExerciceService exerciceService;

    private final DietCloudinaryService cloudinaryService;

    @Autowired
    ImageService imageService;


    @Autowired
    public ExerciceController(ExerciceService exerciceService, DietCloudinaryService cloudinaryService, DefaultUserService userService) {
        this.exerciceService = exerciceService;
        this.cloudinaryService = cloudinaryService;
        this.userService = userService;
    }
    private final DefaultUserService userService;
    @GetMapping("/workoutallUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Exercice createExercice(@RequestBody Exercice exercice) {
        return exerciceService.saveOrUpdateExercice(exercice);
    }
    @PostMapping(value = "/upload-exercice", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<String> uploadExercice(@RequestParam("multipartFile") MultipartFile multipartFile,
                                                 @RequestParam("exerciceName") String exerciceName,
                                                 @RequestParam("muscle") ExerciceType muscle) throws IOException {
        // Create a new Exercice entity
        Exercice exercice = new Exercice();
        exercice.setExerciceName(exerciceName);
        exercice.setMuscle(muscle);

        // Upload image
        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        if (bi == null) {
            return new ResponseEntity<>("Invalid Image!", HttpStatus.BAD_REQUEST);
        }
        Map result = cloudinaryService.upload(multipartFile);
        String imageUrl = (String) result.get("url");

        // Set the image URL for the exercice
        exercice.setExerciceImageUrl(imageUrl);

        // Save the exercice entity
        Exercice savedExercice = exerciceService.saveOrUpdateExercice(exercice);

        // Create and associate the image with the exercice
        Image image = new Image((String) result.get("original_filename"), imageUrl, (String) result.get("public_id"));
        image.setExercice(savedExercice);
        savedExercice.getImages().add(image);
        imageService.save(image);

        return new ResponseEntity<>("Exercice with image added successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/type/{type}")
    public List<Exercice> getExercicesByType(@PathVariable("type") ExerciceType type) {
        return exerciceService.getExercicesByType(type);
    }


    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Exercice updateExercice(@RequestBody Exercice exercice) {
        return exerciceService.saveOrUpdateExercice(exercice);
    }

    @GetMapping("/all")
    public List<Exercice> getAllExercices() {
        return exerciceService.getAllExercices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercice> getExerciceById(@PathVariable Long id) {
        Optional<Exercice> exercice = exerciceService.getExerciceById(id);
        return exercice.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExercice(@PathVariable("id") long id) {

        exerciceService.deleteExercice(id);
    }
    @GetMapping("/Myuser/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable long userId) {
        User user = userService.getUserById(userId);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }
    @PostMapping("/{exerciseId}/associate-exercise-with-user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> associateExerciseWithUser(@PathVariable long exerciseId,
                                                          @PathVariable long userId) {
        try {
            exerciceService.associateExerciseWithUser(exerciseId, userId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{userId}/exercises")
    public ResponseEntity<List<Exercice>> getExercisesByUserId(@PathVariable long userId) {
        List<Exercice> exercises = exerciceService.getExercisesByUserId(userId);
        if (exercises.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(exercises);
        }
    }
}
