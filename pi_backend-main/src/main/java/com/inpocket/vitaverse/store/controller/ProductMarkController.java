package com.inpocket.vitaverse.store.controller;


import com.inpocket.vitaverse.store.entity.ProductMark;
import com.inpocket.vitaverse.store.service.ProductMarkService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/store/product-mark")
public class ProductMarkController {
    private final ProductMarkService productMarkService;

    @GetMapping("/getAllProductMarks")
    public List<ProductMark> getAllProductMarks() {
        return productMarkService.getAllProductMarks();
    }

    @GetMapping("/getProductMarkById/{id}")
    public ResponseEntity<ProductMark> getProductMarkById(@PathVariable Long id) {
        ProductMark productMark = productMarkService.getProductMarkById(id);
        if (productMark != null) {
            return ResponseEntity.ok(productMark);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/createProductMark")
    public ResponseEntity<ProductMark> addProductMark(@RequestBody ProductMark productMark) {
        ProductMark createdProductMark = productMarkService.addProductMark(productMark);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProductMark);
    }

    @PutMapping("/updateProductMark/{id}")
    public ResponseEntity<ProductMark> updateProductMark(@PathVariable Long id, @RequestBody ProductMark productMark) {
        ProductMark updatedProductMark = productMarkService.updateProductMark(id, productMark);
        if (updatedProductMark != null) {
            return ResponseEntity.ok(updatedProductMark);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteProductMark/{id}")
    public ResponseEntity<Void> deleteProductMark(@PathVariable Long id) {
        boolean deleted = productMarkService.deleteProductMark(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
