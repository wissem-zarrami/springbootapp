package com.inpocket.vitaverse.store.controller;


import com.inpocket.vitaverse.store.entity.Product;
import com.inpocket.vitaverse.store.entity.ProductReview;
import com.inpocket.vitaverse.store.service.ProductReviewService;
import com.inpocket.vitaverse.store.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/store/product-reviews")
public class ProductReviewController {
    private final ProductReviewService productReviewService;
    private final ProductService productService;
    @GetMapping("/getReviewsOfProduct/{productId}")
    public ResponseEntity<List<ProductReview>> getReviewsOfProduct(@PathVariable long productId) {
        List<ProductReview> reviews = productReviewService.getReviewsOfProduct(productId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    // Create
    @PostMapping("createProductReview")
    public ResponseEntity<ProductReview> createProductReview(@RequestBody ProductReview productReview) {
        Optional<Product> productOptional = productService.getProductById(productReview.getProductId());
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            productReview.setProduct(product); // Set the product in the product review
            ProductReview createdProductReview = productReviewService.createProductReview(productReview);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProductReview);
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if product with the given ID is not found
        }
    }

    // Read
    @GetMapping("getAllProductReviews")
    public ResponseEntity<List<ProductReview>> getAllProductReviews() {
        List<ProductReview> productReviews = productReviewService.getAllProductReviews();
        return ResponseEntity.ok(productReviews);
    }

    @GetMapping("/getProductReviewById/{id}")
    public ResponseEntity<ProductReview> getProductReviewById(@PathVariable Long id) {
        Optional<ProductReview> productReviewOptional = productReviewService.getProductReviewById(id);
        return productReviewOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/updateProductReview/{id}")
    public ResponseEntity<ProductReview> updateProductReview(@PathVariable Long id, @RequestBody ProductReview updatedProductReview) {
        ProductReview productReview = productReviewService.updateProductReview(id, updatedProductReview);
        return productReview != null ? ResponseEntity.ok(productReview) : ResponseEntity.notFound().build();
    }

    // Delete
    @DeleteMapping("/deleteProductReview/{id}")
    public ResponseEntity<Void> deleteProductReview(@PathVariable Long id) {
        productReviewService.deleteProductReview(id);
        return ResponseEntity.noContent().build();
    }
}
