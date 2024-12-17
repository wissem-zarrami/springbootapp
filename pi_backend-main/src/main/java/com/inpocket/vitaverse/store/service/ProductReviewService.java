package com.inpocket.vitaverse.store.service;


import com.inpocket.vitaverse.store.entity.ProductReview;
import com.inpocket.vitaverse.store.repository.ProductReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductReviewService {
    private final ProductReviewRepository productReviewRepository;

    // Create
    public ProductReview createProductReview(ProductReview productReview) {
        return productReviewRepository.save(productReview);
    }

    // Read
    public List<ProductReview> getAllProductReviews() {
        return productReviewRepository.findAll();
    }

    public Optional<ProductReview> getProductReviewById(Long id) {
        return productReviewRepository.findById(id);
    }

    // Update
    public ProductReview updateProductReview(Long id, ProductReview updatedProductReview) {
        Optional<ProductReview> existingProductReviewOptional = productReviewRepository.findById(id);
        if (existingProductReviewOptional.isPresent()) {
            ProductReview existingProductReview = existingProductReviewOptional.get();
            
            existingProductReview.setComment(updatedProductReview.getComment());
            existingProductReview.setRating(updatedProductReview.getRating());
            existingProductReview.setReviewDate(updatedProductReview.getReviewDate());
            existingProductReview.setProduct(updatedProductReview.getProduct());
            existingProductReview.setProductId(updatedProductReview.getProductId());
            existingProductReview.setUser(updatedProductReview.getUser());

            return productReviewRepository.save(existingProductReview);
        }
        return null; // or throw exception indicating product review not found
    }

    // Delete
    public void deleteProductReview(Long id) {
        productReviewRepository.deleteById(id);
    }

    public  List<ProductReview> getReviewsOfProduct(long productId)
    {
        return productReviewRepository.findByProductProductId(productId);
    }
}
