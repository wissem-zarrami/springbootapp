package com.inpocket.vitaverse.wellbeing.repository;
import com.inpocket.vitaverse.wellbeing.entity.Tip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipRepository extends JpaRepository<Tip, Long>{
}
