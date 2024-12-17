package com.inpocket.vitaverse.wellbeing.service;


import com.inpocket.vitaverse.user.entity.ERole;
import com.inpocket.vitaverse.user.entity.Role;
import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.user.repository.UserRepository;
import com.inpocket.vitaverse.wellbeing.entity.PsychologistCustomerRelation;
import com.inpocket.vitaverse.wellbeing.entity.RelaxationExercice;
import com.inpocket.vitaverse.wellbeing.entity.UserProgress;
import com.inpocket.vitaverse.wellbeing.repository.PsychologistCustomerRelationRepository;
import com.inpocket.vitaverse.wellbeing.repository.RelaxationExerciceRepository;
import com.inpocket.vitaverse.wellbeing.repository.UserProgressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors; // Import pour Collectors.toList()


import java.util.List;

@Service
@Transactional
public class PsychologistCustomerRelationServiceImpl implements PsychologistCustomerRelationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PsychologistCustomerRelationRepository relationRepository;

    @Autowired
    private RelaxationExerciceRepository relaxationExerciceRepository;
    @Autowired

    private UserProgressRepository userProgressRepository;

    public void removePsychologistCustomerRelation(Long psychologistId, Long customerId) {
        // Rechercher la relation entre le psychologue et le client
        PsychologistCustomerRelation relation = relationRepository.findByPsychologistIdAndCustomerId(psychologistId, customerId);

        // Vérifier si la relation existe
        if (relation != null) {
            // Supprimer la relation
            relationRepository.delete(relation);
            System.out.println("Relation between psychologist and customer deleted successfully.");
        } else {
            System.out.println("Relation between psychologist and customer not found.");
        }
    }

    @Override
    public boolean isUserSubscribedToProfessionalRole(Long userId, Long professionalId, ERole professionalRole) {
        // Trouver l'utilisateur par son ID
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user non trouvé"));
        // Obtenir la liste des professionnels auxquels l'utilisateur est abonné
        List<User> subscribedProfessionals = getAllPsychologistsForCustomer(userId);

        // Vérifier si le professionnel spécifié par professionalId a le rôle spécifique
        for (User professional : subscribedProfessionals) {
            // Vérifier si le professionnel a le rôle spécifié
            for (Role role : professional.getRoles()) {
                if (role.getName().equals(professionalRole) && professional.getUserId().equals(professionalId)) {
                    return true; // L'utilisateur est abonné à ce professionnel avec ce rôle
                }
            }
        }

        return false; // L'utilisateur n'est pas abonné à ce professionnel avec ce rôle
    }


    @Override

    public void assignPsychologistToCustomer(Long psychologistId, Long customerId) {
        User psychologist = userRepository.findById(psychologistId).orElseThrow();
        User customer = userRepository.findById(customerId).orElseThrow();

        PsychologistCustomerRelation relation = new PsychologistCustomerRelation();
        relation.setPsychologist(psychologist);
        relation.setCustomer(customer);

        relationRepository.save(relation);
    }
    @Override

    public List<User> getAllPsychologistsForCustomer(Long customerId) {
        return relationRepository.findAllByCustomer_UserId(customerId)
                .stream()
                .map(PsychologistCustomerRelation::getPsychologist)
                .collect(Collectors.toList());
    }
    @Override

    public List<User> getAllCustomersForPsychologist(Long psychologistId) {
        return relationRepository.findAllByPsychologist_UserId(psychologistId)
                .stream()
                .map(PsychologistCustomerRelation::getCustomer)
                .collect(Collectors.toList());
    }

    @Override
    public void assignRelaxationExerciseToCustomer(Long psychologistId, Long customerId, RelaxationExercice relaxationExercise) {
        User psychologist = userRepository.findById(psychologistId).orElseThrow();
        User customer = userRepository.findById(customerId).orElseThrow();

        // Vérifie si l'exercice de relaxation existe déjà en base de données
        RelaxationExercice existingExercise = relaxationExerciceRepository.findRelaxationExerciceByExerciseRelaxationName(relaxationExercise.getExerciseRelaxationName());

        // Si l'exercice de relaxation n'existe pas encore en base de données, sauvegardez-le
        RelaxationExercice savedExercise;
        if (existingExercise == null) {
            savedExercise = relaxationExerciceRepository.save(relaxationExercise);
        } else {
            savedExercise = existingExercise;
        }

        // Créez un nouvel enregistrement UserProgress
        UserProgress userProgress = new UserProgress();
        userProgress.setProgressUser(customer);
        userProgress.setRelaxationExercice(savedExercise);

        // Sauvegarde de l'enregistrement UserProgress
        userProgressRepository.save(userProgress);
    }

}



