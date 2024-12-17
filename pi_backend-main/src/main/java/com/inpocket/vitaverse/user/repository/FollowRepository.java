package com.inpocket.vitaverse.user.repository;
import com.inpocket.vitaverse.user.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long>{
}
