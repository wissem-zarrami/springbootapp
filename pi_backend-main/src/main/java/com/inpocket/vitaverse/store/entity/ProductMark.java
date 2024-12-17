package com.inpocket.vitaverse.store.entity;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class ProductMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long productMarkId;
    String name;
    String description;
}
