package com.inpocket.vitaverse.user.repository;

import com.inpocket.vitaverse.user.entity.ERole;
import com.inpocket.vitaverse.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
