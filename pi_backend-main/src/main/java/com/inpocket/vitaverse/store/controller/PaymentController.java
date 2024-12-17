package com.inpocket.vitaverse.store.controller;

import com.inpocket.vitaverse.store.entity.Payment;
import com.inpocket.vitaverse.store.entity.PhysicalStore;
import com.inpocket.vitaverse.store.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/store/payment")
public class PaymentController {
    PaymentService paymentService;

    @GetMapping("/getPaymentById/{id}")
        public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Optional<Payment> physicalStoreOptional = paymentService.getPaymentById(id);
        return physicalStoreOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deletePayment/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getPaymentByUserId/{userId}")
    public ResponseEntity<Payment> getPaymentByUserId(@PathVariable Long userId) {
        Optional<Payment> physicalStoreOptional = paymentService.getPaymentByUserId(userId);
        return physicalStoreOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getPaymentByShipmentUser/{shipmentUserId}")
    public ResponseEntity<Payment> getPaymentByShipmentUser(@PathVariable Long shipmentUserId) {
        Optional<Payment> physicalStoreOptional = paymentService.getPaymentByShipmentUser(shipmentUserId);
        return physicalStoreOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
