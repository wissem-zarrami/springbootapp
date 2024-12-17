package com.inpocket.vitaverse.wellbeing.entity;

import com.inpocket.vitaverse.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class PsychologistCustomerRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "psychologist_id")
    private User psychologist;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;
}
