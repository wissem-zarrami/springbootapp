package com.inpocket.vitaverse.wellbeing.controller;
import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.user.security.services.UserService;
import com.inpocket.vitaverse.wellbeing.entity.RelaxationExercice;
import com.inpocket.vitaverse.wellbeing.entity.RelaxationExerciceType;
import com.inpocket.vitaverse.wellbeing.service.RelaxationExerciceService;
import com.inpocket.vitaverse.wellbeing.service.RelaxationExerciceService;
import com.inpocket.vitaverse.wellbeing.service.UserProgressService;
import com.inpocket.vitaverse.wellbeing.service.WCloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/relaxation-exercises")
public class RelaxationExerciceController {
    private final RelaxationExerciceService relaxationExerciseService;
    private final WCloudinaryService wCloudinaryService;

    private  final UserService userService;


    @Autowired
    public RelaxationExerciceController(RelaxationExerciceService relaxationExerciseService, WCloudinaryService wCloudinaryService,UserService userService) {
        this.relaxationExerciseService = relaxationExerciseService;
        this.wCloudinaryService = wCloudinaryService;
        this.userService=userService;

    }

    @DeleteMapping("/{id}")
    public void deleteExerciceAndUserProgress(@PathVariable long id) {
        relaxationExerciseService.deleteExerciceFromUserProgress(id);
    }

    @GetMapping("/{id}")
    public RelaxationExercice getRelaxationExerciceById(@PathVariable long id) {
        Optional<RelaxationExercice> relaxationExercice = relaxationExerciseService.getRelaxationExerciceById(id);
        return relaxationExercice.orElse(null);
    }

    @PostMapping
    public RelaxationExercice saveRelaxationExercice(@RequestBody RelaxationExercice relaxationExercice) {
        return relaxationExerciseService.saveRelaxationExercice(relaxationExercice);
    }

   /* @DeleteMapping("/{id}")
    public void deleteRelaxationExerciceById(@PathVariable long id) {
        relaxationExerciseService.deleteRelaxationExerciceById(id);
    }*/

    @GetMapping("/all-exercises")
    @ResponseBody
    public ResponseEntity<List<RelaxationExercice>> getAllRelaxationExercises() {
        List<RelaxationExercice> exercises = relaxationExerciseService.getAllRelaxationExercices();
        if (exercises.isEmpty()) {
            return ResponseEntity.noContent().build(); // Aucun contenu trouvé
        } else {
            return ResponseEntity.ok(exercises); // Retourne la liste des exercices
        }
    }
    @PutMapping("/update-exercise/{id}")
    public ResponseEntity<?> updateRelaxationExercice(@PathVariable("id") long id, @RequestBody RelaxationExercice updatedExercice) {
        try {
            relaxationExerciseService.updateRelaxationExercice(id, updatedExercice);
            return new ResponseEntity<>("RelaxationExercice updated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/upload-exercise", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<String> uploadExercise(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("name") String name,
            @RequestParam("instructions") String instructions,
            @RequestParam("duration") int duration,
            @RequestParam("type") RelaxationExerciceType type
    ) {
        try {
            Map<String, String> uploadResult = wCloudinaryService.uploadVideoFromMultipartFile(multipartFile);

            RelaxationExercice exercise = new RelaxationExercice();
            exercise.setExerciseRelaxationName(name);
            exercise.setInstructions(instructions);
            exercise.setDuration(duration);
            exercise.setRelaxationExerciceType(type);
            exercise.setVideoUrl(uploadResult.get("url")); // Assuming Cloudinary returns the URL

            relaxationExerciseService.saveRelaxationExercice(exercise);

            return ResponseEntity.ok("Exercise uploaded successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload exercise: " + e.getMessage());
        }
    }


    @Autowired
    private UserProgressService userProgressService;
    @GetMapping("/user/{userId}/relaxation-exercises")
    public List<RelaxationExercice> getRelaxationExercisesForUser(@PathVariable Long userId) {
        return userProgressService.getRelaxationExercisesForUser(userId);
    }

    @PostMapping("/mark-exercise-as-watched/{userId}/{exerciseId}")
    public ResponseEntity<?> markExerciseAsWatched(@PathVariable Long userId, @PathVariable Long exerciseId) {
        try {
            userProgressService.markExerciseAsWatched(userId, exerciseId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to mark exercise as watched.");
        }
    }



    @GetMapping("/exercises-count/{userId}")
    public ResponseEntity<Long> countExercisesForUser(@PathVariable Long userId) {
        Long exercisesCount = userProgressService.countExercisesForUser(userId);
        return ResponseEntity.ok(exercisesCount);
    }

    @GetMapping("/relaxation-completed/{userId}/{exerciseId}")
    public ResponseEntity<Boolean> isRelaxationExerciseCompleted(@PathVariable Long userId, @PathVariable Long exerciseId) {
        // Récupérer l'utilisateur par son ID
        Optional<User> userOptional = userService.getUserById(userId);

        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();

        boolean isCompleted = userProgressService.isRelaxationExerciseCompletedForUser(user, exerciseId);

        return new ResponseEntity<>(isCompleted, HttpStatus.OK);
    }
}
