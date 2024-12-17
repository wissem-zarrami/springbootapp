package com.inpocket.vitaverse.user.repository;
import com.inpocket.vitaverse.user.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{
}
