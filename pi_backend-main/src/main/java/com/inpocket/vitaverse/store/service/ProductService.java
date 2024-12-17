package com.inpocket.vitaverse.store.service;

import com.inpocket.vitaverse.store.Shared;
import com.inpocket.vitaverse.store.entity.Payment;
import com.inpocket.vitaverse.store.entity.Product;
import com.inpocket.vitaverse.store.repository.PaymentRepository;
import com.inpocket.vitaverse.store.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final PaymentRepository paymentRepository;

    // Create
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // Read
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Update
    public Product updateProduct(Long id, Product updatedProduct) {
        Optional<Product> existingProductOptional = productRepository.findById(id);
        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setTechnicalName(updatedProduct.getTechnicalName());
            existingProduct.setTechnicalDescription(updatedProduct.getTechnicalDescription());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setQuantity(updatedProduct.getQuantity());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setMark(updatedProduct.getMark());
            existingProduct.setDisponibility(updatedProduct.getDisponibility());
            existingProduct.setSeller(updatedProduct.getSeller());
            existingProduct.setStores(updatedProduct.getStores());

            return productRepository.save(existingProduct);
        }
        return null; // or throw exception indicating product not found
    }

    public void updateProductImages(Product product, MultipartFile[] images)
    {
        List<String> imagePaths = new ArrayList<>();
        if (images != null) {
            for (MultipartFile image : images) {
                String fileName = image.getOriginalFilename();
                try {
                    // Save image to filesystem
                    Path imagePath = Paths.get(Shared.UPLOAD_DIRECTORY + "/" + fileName);
                    Files.write(imagePath, image.getBytes());
                    // Store image path
                    imagePaths.add("assets/uploads/"+imagePath.getFileName().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle error
                }
            }
        }
        // Update product images
        product.setImages(imagePaths);
        productRepository.save(product);
    }

    // Delete
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> searchProducts(String name,
                                 int minPrice,
                                 int maxPrice,
                                 String categoryName,
                                 String markName,
                                 String userName)
    {
        System.out.println("Params: name=" + name + ", minPrice=" + minPrice
                + ", maxPrice=" + maxPrice + ", categoryName=" + categoryName
                + ", markName=" + markName + ", userName=" + userName);
        return productRepository.searchProducts(name.trim(), minPrice, maxPrice, markName.trim(), userName.trim());
        //return productRepository.searchProducts(name);
    }

    // returns products from last hours (eg. last 42h)
    public List<Product> getNewProducts(int hours)
    {
        LocalDateTime cutoffDateTime = LocalDateTime.now().minusHours(hours);
        return productRepository.findByCreatedAtAfter(cutoffDateTime);
    }

    // returns products from last hours that were bought the most
    /*public List<Product> getHotProducts(int hours)
    {
        // Calculate the date and time 'hours' hours ago from now
        LocalDateTime cutoffDateTime = LocalDateTime.now().minusHours(hours);

        // Retrieve all payments made within the last 'hours' hours
        List<Payment> recentPayments = paymentRepository.findByPaymentDateAfter(cutoffDateTime);

        // Group payments by bought product and count the number of purchases
        Map<Product, Long> productPurchaseCounts = recentPayments.stream()
                .collect(Collectors.groupingBy(Payment::getBoughtProduct, Collectors.counting()));

        // Sort products by purchase count in descending order
        List<Product> hotProducts = productPurchaseCounts.entrySet().stream()
                .sorted(Map.Entry.<Product, Long>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return hotProducts;
    }*/
}
