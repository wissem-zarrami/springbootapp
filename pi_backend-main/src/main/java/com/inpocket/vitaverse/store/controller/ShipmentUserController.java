package com.inpocket.vitaverse.store.controller;

import com.inpocket.vitaverse.store.entity.ShipmentUser;
import com.inpocket.vitaverse.store.service.PaymentService;
import com.inpocket.vitaverse.store.service.ShipmentUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/store/shipmentUser")
public class ShipmentUserController {
    private final ShipmentUserService shipmentUserService;
    private final PaymentService paymentService;

    // Create
    @PostMapping("/createShipmentUser")
    public ResponseEntity<ShipmentUser> createShipmentUser(@RequestBody ShipmentUser shipmentUser) {
        ShipmentUser createdShipmentUser = shipmentUserService.createShipmentUser(shipmentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShipmentUser);
    }

    // Read
    @GetMapping("/getAllShipmentUsers")
    public ResponseEntity<List<ShipmentUser>> getAllShipmentUsers() {
        List<ShipmentUser> shipmentUsers = shipmentUserService.getAllShipmentUsers();
        return ResponseEntity.ok(shipmentUsers);
    }

    @GetMapping("/getShipmentUserById/{id}")
    public ResponseEntity<ShipmentUser> getShipmentUserById(@PathVariable Long id) {
        Optional<ShipmentUser> shipmentUserOptional = shipmentUserService.getShipmentUserById(id);
        return shipmentUserOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/updateShipmentUser/{id}")
    public ResponseEntity<ShipmentUser> updateShipmentUser(@PathVariable Long id, @RequestBody ShipmentUser updatedShipmentUser) {
        ShipmentUser shipmentUser = shipmentUserService.updateShipmentUser(id, updatedShipmentUser);
        return shipmentUser != null ? ResponseEntity.ok(shipmentUser) : ResponseEntity.notFound().build();
    }

    // Delete
    @DeleteMapping("/deleteShipmentUser/{id}")
    public ResponseEntity<Void> deleteShipmentUser(@PathVariable Long id) {
        shipmentUserService.deleteShipmentUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/setShipLocation/{userId}/{shipLocation}")
    public void setShipLocation(@PathVariable Long userId, @PathVariable String shipLocation) {

        paymentService.setShipLocation(userId, shipLocation);
    }

    @GetMapping("/getShipDistance/{id}/{userLocation}")
    public float getShipDistance(@PathVariable Long id, @PathVariable String userLocation)
    {
        return paymentService.getShipDistance(id, userLocation);
    }
}

