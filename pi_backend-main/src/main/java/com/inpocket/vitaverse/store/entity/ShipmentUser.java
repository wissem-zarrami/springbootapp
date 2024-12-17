package com.inpocket.vitaverse.store.entity;

import com.inpocket.vitaverse.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ShipmentUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long shipmentUserId;
    boolean isAvailable;
    @OneToOne
    User user;
}
