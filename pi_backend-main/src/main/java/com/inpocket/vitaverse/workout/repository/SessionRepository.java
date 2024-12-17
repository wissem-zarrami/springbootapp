package com.inpocket.vitaverse.workout.repository;
import com.inpocket.vitaverse.workout.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long>{
    List<Session> findByUsersUserId(Long userId);
}
