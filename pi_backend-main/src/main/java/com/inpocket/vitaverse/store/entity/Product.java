package com.inpocket.vitaverse.store.entity;

import com.inpocket.vitaverse.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long productId;
    ProductCategory category;
    @ManyToOne
    ProductMark mark;
    ProductDisponibility disponibility;
    String name;
    String description;
    String technicalName;
    String technicalDescription;
    Date createdAt;
    float price;
    int quantity;
    @ManyToOne
    User seller;
    //@ManyToMany
    //List<PhysicalStore> physicalStore;
    @ElementCollection
    @CollectionTable(name = "product_images")
    @Column(name = "image_url")
    private List<String> images;
    @OneToMany(mappedBy = "product")
    List<ProductReview> reviews;
    @ManyToMany
    List<PhysicalStore> stores;
}
