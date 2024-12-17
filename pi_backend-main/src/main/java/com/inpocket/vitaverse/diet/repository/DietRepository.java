package com.inpocket.vitaverse.diet.repository;
import com.inpocket.vitaverse.diet.entity.Diet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DietRepository extends JpaRepository<Diet, Long>{
}
