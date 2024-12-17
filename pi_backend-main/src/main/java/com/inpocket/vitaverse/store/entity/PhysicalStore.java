package com.inpocket.vitaverse.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class PhysicalStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long physicalStoreId;

    String name;
    String description;
    String location;
    String workHours;
    String image;

    @JsonIgnore
    @ManyToMany(mappedBy = "stores")
    List<Product> products;
}
