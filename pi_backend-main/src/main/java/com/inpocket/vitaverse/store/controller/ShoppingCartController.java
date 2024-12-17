package com.inpocket.vitaverse.store.controller;


import com.inpocket.vitaverse.store.entity.ShoppingCart;
import com.inpocket.vitaverse.store.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/store/shopping-cart")
public class ShoppingCartController {
    ShoppingCartService shoppingCartService;

    // Create
    @PostMapping("/createShoppingCart")
    public ResponseEntity<ShoppingCart> createShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        ShoppingCart createdShoppingCart = shoppingCartService.createShoppingCart(shoppingCart);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShoppingCart);
    }

    // Read
    @GetMapping("/getAllShoppingCarts")
    public ResponseEntity<List<ShoppingCart>> getAllShoppingCarts() {
        List<ShoppingCart> shoppingCarts = shoppingCartService.getAllShoppingCarts();
        return ResponseEntity.ok(shoppingCarts);
    }

    @GetMapping("/getShoppingCartById/{id}")
    public ResponseEntity<ShoppingCart> getShoppingCartById(@PathVariable Long id) {
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartService.getShoppingCartById(id);
        return shoppingCartOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/updateShoppingCart/{id}")
    public ResponseEntity<ShoppingCart> updateShoppingCart(@PathVariable Long id, @RequestBody ShoppingCart updatedShoppingCart) {
        ShoppingCart shoppingCart = shoppingCartService.updateShoppingCart(id, updatedShoppingCart);
        return shoppingCart != null ? ResponseEntity.ok(shoppingCart) : ResponseEntity.notFound().build();
    }

    // Delete
    @DeleteMapping("/deleteShoppingCart/{id}")
    public ResponseEntity<Void> deleteShoppingCart(@PathVariable Long id) {
        shoppingCartService.deleteShoppingCart(id);
        return ResponseEntity.noContent().build();
    }


    /*@PostMapping("/{cartId}/addProduct/{productId}")
    public ResponseEntity<ShoppingCart> addProductToCart(@PathVariable Long cartId, @PathVariable Long productId) {
        ShoppingCart updatedCart = shoppingCartService.addProductToCart(cartId, productId);
        if (updatedCart != null) {
            return ResponseEntity.ok(updatedCart);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/



    @GetMapping("/getShoppingCartByUserId/{userId}")
    public ResponseEntity<ShoppingCart> getShoppingCartByUserIdCreateIfNotExist(@PathVariable Long userId) {
        ShoppingCart cart = shoppingCartService.getShoppingCartByUserIdCreateIfNotExist(userId);
        if (cart != null) {
            return ResponseEntity.ok(cart);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/addProductToCart/{productId}/{userId}")
    public ResponseEntity<ShoppingCart> addProductToCart(@PathVariable Long userId, @PathVariable Long productId) {
        ShoppingCart updatedCart = shoppingCartService.addProductToCart(userId, productId);
        if (updatedCart != null) {
            return ResponseEntity.ok(updatedCart);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/checkoutCart/{userId}")
    public void checkoutCart(@PathVariable Long userId)
    {
        shoppingCartService.checkoutCart(userId);
    }
}
