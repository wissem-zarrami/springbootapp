package com.inpocket.vitaverse.store.entity;

import com.inpocket.vitaverse.user.entity.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long shoppingCartId;

    @OneToOne
    User user;
    @ManyToMany //many to many cuz products have quantity. so if its in a shopping cart it can be not over
    List<Product> products;
    @OneToOne
    Payment payment;

    @Transient
    float totalPrice;
    public float getTotalPrice()
    {
        float t = 0;
        for(var p : products)
        {
            t+=p.getPrice();
        }
        return t;
    }
}
