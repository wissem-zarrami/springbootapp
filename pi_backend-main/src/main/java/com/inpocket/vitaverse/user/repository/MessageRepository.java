
package com.inpocket.vitaverse.user.repository;

import com.inpocket.vitaverse.user.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

}



