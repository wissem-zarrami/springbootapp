package com.inpocket.vitaverse.store.controller;

import com.inpocket.vitaverse.store.entity.Product;
import com.inpocket.vitaverse.store.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/store/products")
public class ProductController {
    private final ProductService productService;

    // Create
    @PostMapping("/createProduct")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/updateProductImages/{id}")
    public ResponseEntity<Product> updateProductImages(@PathVariable Long id, @RequestBody MultipartFile[] images) {
        Optional<Product> product = productService.getProductById(id);
        if(product.isPresent())
        {
            productService.updateProductImages(product.get(), images);
            return ResponseEntity.ok(product.get());
        }
        return  ResponseEntity.notFound().build();
    }

    // Read
    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/getProductById/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> productOptional = productService.getProductById(id);
        return productOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product product = productService.updateProduct(id, updatedProduct);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    // Delete
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/searchProducts")
    public List<Product> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String markName,
            @RequestParam(required = false) String userName) {

        return productService.searchProducts(name, minPrice, maxPrice, categoryName, markName, userName);
    }

}
