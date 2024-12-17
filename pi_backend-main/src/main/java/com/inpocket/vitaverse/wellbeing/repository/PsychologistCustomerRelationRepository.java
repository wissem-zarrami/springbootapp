package com.inpocket.vitaverse.wellbeing.repository;

import com.inpocket.vitaverse.wellbeing.entity.PsychologistCustomerRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PsychologistCustomerRelationRepository extends JpaRepository<PsychologistCustomerRelation, Long> {
    List<PsychologistCustomerRelation> findAllByCustomer_UserId(Long customerId);
    List<PsychologistCustomerRelation> findAllByPsychologist_UserId(Long psychologistId);
    @Query("SELECT r FROM PsychologistCustomerRelation r WHERE r.psychologist.userId = :psychologistId AND r.customer.userId = :customerId")
    PsychologistCustomerRelation findByPsychologistIdAndCustomerId(@Param("psychologistId") Long psychologistId, @Param("customerId") Long customerId);

}
