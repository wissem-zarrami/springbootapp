package com.inpocket.vitaverse.wellbeing.controller;
import com.inpocket.vitaverse.wellbeing.entity.Rating;
import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.user.repository.UserRepository;
import com.inpocket.vitaverse.wellbeing.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/ratings")
public class RatingController {
    @Autowired
    private RatingService ratingService;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/create")
    public ResponseEntity<Rating> createRating(@RequestParam Long ratedUserId, @RequestParam Long ratingUserId, @RequestParam int ratingValue) {
        // Assuming you retrieve the users from the database, or you can pass the user objects directly
        User ratedUser = userRepository.findById(ratedUserId).orElse(null);
        User ratingUser = userRepository.findById(ratingUserId).orElse(null);
        if (ratedUser == null || ratingUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Rating newRating = ratingService.createRating(ratedUser, ratingUser, ratingValue);
        return ResponseEntity.ok(newRating);
    }
    // Other endpoints for CRUD operations can be added here
    @GetMapping("/average-rating/{ratedUserId}")
    public ResponseEntity<Integer> getAverageRatingForRatedUser(@PathVariable Long ratedUserId) {
        int averageRatingValue = ratingService.getRatingValueForRatedUser(ratedUserId);
        return ResponseEntity.ok(averageRatingValue);
    }
}