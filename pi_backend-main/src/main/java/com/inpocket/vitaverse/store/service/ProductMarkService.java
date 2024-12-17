package com.inpocket.vitaverse.store.service;


import com.inpocket.vitaverse.store.entity.ProductMark;
import com.inpocket.vitaverse.store.repository.ProductMarkRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductMarkService {
    private final ProductMarkRepository productMarkRepository;

    public List<ProductMark> getAllProductMarks() {
        return productMarkRepository.findAll();
    }

    public ProductMark getProductMarkById(Long id) {
        return productMarkRepository.findById(id).orElse(null);
    }

    public ProductMark addProductMark(ProductMark productMark) {
        return productMarkRepository.save(productMark);
    }

    public ProductMark updateProductMark(Long id, ProductMark updatedProductMark) {
        ProductMark existingProductMark = productMarkRepository.findById(id).orElse(null);
        if (existingProductMark != null) {
            existingProductMark.setName(updatedProductMark.getName());
            existingProductMark.setDescription(updatedProductMark.getDescription());
            // Update other fields as needed
            return productMarkRepository.save(existingProductMark);
        }
        return null;
    }

    public boolean deleteProductMark(Long id) {
        ProductMark existingProductMark = productMarkRepository.findById(id).orElse(null);
        if (existingProductMark != null) {
            productMarkRepository.delete(existingProductMark);
            return true;
        }
        return false;
    }
}

