package com.inpocket.vitaverse.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inpocket.vitaverse.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long paymentId;

    String paymentName;
    String paymentDescription;
    Date paymentDate;
    String shipmentLocation;
    //PaymentMethod paymentMethod;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL) // so that when payment deleted, its cart deleted
    ShoppingCart shoppingCart; //you can get buyer from here, no need to add it in this class
    @JsonIgnore
    @ManyToOne
    ShipmentUser shipmentUser;
}
