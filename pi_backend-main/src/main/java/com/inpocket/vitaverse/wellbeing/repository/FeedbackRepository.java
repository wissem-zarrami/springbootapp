package com.inpocket.vitaverse.wellbeing.repository;
import com.inpocket.vitaverse.wellbeing.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long>{
}
