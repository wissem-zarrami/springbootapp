package com.inpocket.vitaverse.store.service;


import com.inpocket.vitaverse.store.entity.Payment;
import com.inpocket.vitaverse.store.entity.Product;
import com.inpocket.vitaverse.store.entity.ShoppingCart;
import com.inpocket.vitaverse.store.repository.PaymentRepository;
import com.inpocket.vitaverse.store.repository.ShipmentUserRepository;
import com.inpocket.vitaverse.store.repository.ShoppingCartRepository;
import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.user.service.DefaultUserService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final PaymentRepository paymentRepository;
    private final DefaultUserService userService;
    private final ProductService productService;
    private final EmailService emailService;
    private final ShipmentUserRepository shipmentUserRepository;

    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    public List<ShoppingCart> getAllShoppingCarts() {
        return shoppingCartRepository.findAll();
    }

    public Optional<ShoppingCart> getShoppingCartById(Long id) {
        return shoppingCartRepository.findById(id);
    }

    public ShoppingCart updateShoppingCart(Long id, ShoppingCart updatedShoppingCart) {
        Optional<ShoppingCart> existingShoppingCartOptional = shoppingCartRepository.findById(id);
        if (existingShoppingCartOptional.isPresent()) {
            updatedShoppingCart.setShoppingCartId(id);
            return shoppingCartRepository.save(updatedShoppingCart);
        } else {
            return null; // Or throw an exception indicating not found
        }
    }

    public void deleteShoppingCart(Long id) {
        shoppingCartRepository.deleteById(id);
    }


    public Optional<ShoppingCart> getShoppingCartByUserId(Long userId) {
        return Optional.ofNullable(userService.getUserById(userId))
                .flatMap(shoppingCartRepository::findByUser);
    }
    public ShoppingCart getShoppingCartByUserIdCreateIfNotExist(Long userId) {
        Optional<User> userOptional = Optional.ofNullable(userService.getUserById(userId));
        return userOptional.map(this::findOrCreateShoppingCartForUser).orElse(null);
    }

    public ShoppingCart addProductToCart(Long userId, Long productId) {
        // Retrieve user
        Optional<User> userOptional = Optional.ofNullable(userService.getUserById(userId));
        if (userOptional.isEmpty()) {
            return null; // User not found
        }
        User user = userOptional.get();

        // Retrieve or create shopping cart for user
        ShoppingCart cart = findOrCreateShoppingCartForUser(user);

        // Retrieve product
        Optional<Product> productOptional = productService.getProductById(productId);
        if (productOptional.isEmpty()) {
            return null; // Product not found
        }
        Product product = productOptional.get();

        // Add product to cart if it doesn't exist
        if (!cart.getProducts().contains(product)) {
            cart.getProducts().add(product);
        }

        System.out.println(cart.getShoppingCartId());

        // Save or update shopping cart
        return shoppingCartRepository.save(cart);
    }

    // Helper method to find or create shopping cart for user
    private ShoppingCart findOrCreateShoppingCartForUser(User user) {
        Optional<ShoppingCart> cartOptional = shoppingCartRepository.findByUser(user);
        if (cartOptional.isPresent()) {
            return cartOptional.get();
        } else {
            ShoppingCart newCart = new ShoppingCart();
            newCart.setUser(user);
            newCart.setProducts(new ArrayList<>());
            return shoppingCartRepository.save(newCart);
        }
    }
    public static String readFileToString(String path) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(path);
        return asString(resource);
    }
    public static String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void checkoutCart(Long userId) throws RuntimeException
    {
        Optional<User> userOpt = Optional.ofNullable(userService.getUserById(userId));
        if(userOpt.isPresent())
        {
            var user = userOpt.get();
            var cart = findOrCreateShoppingCartForUser(user);

            if(cart.getPayment() != null) //if already paid, return
                return;

            var shipmentUser = shipmentUserRepository.findByIsAvailable(true);
            if(shipmentUser.isPresent())
            {
                var shipmentUserValue = shipmentUser.get();
                Payment payment = new Payment();
                payment.setShoppingCart(cart);
                payment.setPaymentDate(Calendar.getInstance().getTime());
                payment.setShipmentUser(shipmentUserValue);
                cart.setPayment(payment);
                paymentRepository.save(payment);
                shoppingCartRepository.save(cart);
            }
            else
            {
                throw new RuntimeException("There's no available shipment user rn. please try later");
            }

            String email = user.getEmail();
            System.out.println("Sending store email to: " + email + "...");
            try {
                String templateHTML = readFileToString("storeMailThanksForBuyingTemplate.html");
                templateHTML = templateHTML.replace("[Customer Name]", user.getUsername());
                templateHTML = templateHTML.replace("[Your Store Name]", "VitaVerse");
                templateHTML = templateHTML.replace("[Total Amount]"
                        , Float.toString(cart.getTotalPrice()));
                String products = "";
                for(var product : cart.getProducts())
                {
                    products += "<li>Product : " + product.getName() + "</li>\n";
                }
                templateHTML = templateHTML.replace("[Products]", products);
                emailService.sendHtmlEmail("aymen.ayoo@gmail.com",
                        "[VitaVerse] Thank you for purchasing !",
                        templateHTML);
            } catch (MessagingException e) {
                System.out.print("[Error] " + e);
            }

            System.out.println("email was sent successfuly");
        }
    }
}
