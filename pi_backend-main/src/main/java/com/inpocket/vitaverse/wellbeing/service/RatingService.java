package com.inpocket.vitaverse.wellbeing.service;
import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.wellbeing.entity.Rating;

import java.util.List;

public interface RatingService {

    int getRatingValueForRatedUser(Long ratedUserId);


    Rating createRating(User ratedUser, User ratingUser, int ratingValue);
}