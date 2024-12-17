package com.inpocket.vitaverse.wellbeing.service;
import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.wellbeing.repository.RatingRepository;
import com.inpocket.vitaverse.user.repository.UserRepository;
import com.inpocket.vitaverse.wellbeing.entity.Rating;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class RatingImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Rating createRating(User ratedUser, User ratingUser, int ratingValue) {
        Rating rating = new Rating();
        rating.setRatedUser(ratedUser);
        rating.setRatingUser(ratingUser);
        rating.setRatingValue(ratingValue);
        // You can add more logic/validation here if needed before saving
        return ratingRepository.save(rating);

    }
    @Override
    public int getRatingValueForRatedUser(Long ratedUserId) {
        // Find the user by ID
        User ratedUser = userRepository.findById(ratedUserId).orElse(null);
        if (ratedUser == null) {
            // Handle the case where the user with the given ID does not exist
            return 0; // Or whatever default value you prefer
        }

        // Get the ratings for the rated user
        List<Rating> ratings = ratingRepository.findByRatedUser(ratedUser);
        if (ratings.isEmpty()) {
            return 0; // Or whatever default value you prefer
        }

        // Calculate the average rating value
        int sum = ratings.stream().mapToInt(Rating::getRatingValue).sum();
        return sum / ratings.size();
    }}