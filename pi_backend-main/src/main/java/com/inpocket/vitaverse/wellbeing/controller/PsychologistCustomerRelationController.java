package com.inpocket.vitaverse.wellbeing.controller;

import com.inpocket.vitaverse.user.entity.ERole;
import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.wellbeing.entity.RelaxationExercice;
import com.inpocket.vitaverse.wellbeing.service.PsychologistCustomerRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/api/psychologist-customer-relation")
public class PsychologistCustomerRelationController {

    @Autowired
    private PsychologistCustomerRelationService relationService;

    @PostMapping("/assign/{psychologistId}/{customerId}")
    public ResponseEntity<String> assignPsychologistToCustomer(@PathVariable Long psychologistId, @PathVariable Long customerId) {
        relationService.assignPsychologistToCustomer(psychologistId, customerId);
        return ResponseEntity.ok("Psychologist assigned to customer successfully.");
    }



    @GetMapping("/customers/{psychologistId}")
    public ResponseEntity<List<User>> getAllCustomersForPsychologist(@PathVariable Long psychologistId) {
        List<User> customers = relationService.getAllCustomersForPsychologist(psychologistId);
        return ResponseEntity.ok(customers);


    }

    @PostMapping("/assign-relaxation-exercise/{psychologistId}/{customerId}")
    public ResponseEntity<String> assignRelaxationExerciseToCustomer(@PathVariable Long psychologistId,
                                                                     @PathVariable Long customerId,
                                                                     @RequestBody RelaxationExercice relaxationExercise) {
        relationService.assignRelaxationExerciseToCustomer(psychologistId, customerId, relaxationExercise);
        return ResponseEntity.ok("Relaxation exercise assigned to customer successfully.");
    }

    @GetMapping("/check/{userId}/{professionalId}/{professionalRole}")
    public boolean isUserSubscribedToProfessionalRole(@PathVariable Long userId,
                                                      @PathVariable Long professionalId,
                                                      @PathVariable ERole professionalRole) {
        return relationService.isUserSubscribedToProfessionalRole(userId, professionalId, professionalRole);
    }

    @DeleteMapping("/psychologist/{psychologistId}/customer/{customerId}")
    public ResponseEntity<String> removePsychologistCustomerRelation(@PathVariable Long psychologistId, @PathVariable Long customerId) {
        try {
            relationService.removePsychologistCustomerRelation(psychologistId, customerId);
            return ResponseEntity.ok("Relation between psychologist and customer deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete relation between psychologist and customer: " + e.getMessage());
        }
    }

}
