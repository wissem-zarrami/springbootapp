package com.inpocket.vitaverse.wellbeing.repository;
import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.wellbeing.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query("SELECT r FROM Rating r WHERE r.ratedUser = :ratedUser")
    List<Rating> findByRatedUser(@Param("ratedUser") User ratedUser);
}