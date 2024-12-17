package com.inpocket.vitaverse.wellbeing.service;
import com.inpocket.vitaverse.user.entity.ERole;
import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.wellbeing.entity.RelaxationExercice;

import java.util.List;
public interface PsychologistCustomerRelationService {
    void assignPsychologistToCustomer(Long psychologistId, Long customerId);

    List<User> getAllPsychologistsForCustomer(Long customerId);

    List<User> getAllCustomersForPsychologist(Long psychologistId);
    void assignRelaxationExerciseToCustomer(Long psychologistId, Long customerId, RelaxationExercice relaxationExercise) ;

     boolean isUserSubscribedToProfessionalRole(Long userId, Long professionalId, ERole professionalRole);

    void removePsychologistCustomerRelation(Long psychologistId, Long customerId);
}