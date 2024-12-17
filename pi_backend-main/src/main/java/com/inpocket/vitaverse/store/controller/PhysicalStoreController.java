package com.inpocket.vitaverse.store.controller;


import com.inpocket.vitaverse.store.entity.PhysicalStore;
import com.inpocket.vitaverse.store.service.PhysicalStoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/store/physical-stores")
public class PhysicalStoreController {
    private final PhysicalStoreService physicalStoreService;

    // Create
    @PostMapping("/createPhysicalStore")
    public ResponseEntity<PhysicalStore> createPhysicalStore(@RequestBody PhysicalStore physicalStore) {
        PhysicalStore createdPhysicalStore = physicalStoreService.createPhysicalStore(physicalStore);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPhysicalStore);
    }

    // Read
    @GetMapping("/getAllPhysicalStores")
    public ResponseEntity<List<PhysicalStore>> getAllPhysicalStores() {
        List<PhysicalStore> physicalStores = physicalStoreService.getAllPhysicalStores();
        return ResponseEntity.ok(physicalStores);
    }

    @GetMapping("/getPhysicalStoreById/{id}")
    public ResponseEntity<PhysicalStore> getPhysicalStoreById(@PathVariable Long id) {
        Optional<PhysicalStore> physicalStoreOptional = physicalStoreService.getPhysicalStoreById(id);
        return physicalStoreOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/updatePhysicalStore/{id}")
    public ResponseEntity<PhysicalStore> updatePhysicalStore(@PathVariable Long id, @RequestBody PhysicalStore updatedPhysicalStore) {
        PhysicalStore physicalStore = physicalStoreService.updatePhysicalStore(id, updatedPhysicalStore);
        return physicalStore != null ? ResponseEntity.ok(physicalStore) : ResponseEntity.notFound().build();
    }

    // Delete
    @DeleteMapping("/deletePhysicalStore/{id}")
    public ResponseEntity<Void> deletePhysicalStore(@PathVariable Long id) {
        physicalStoreService.deletePhysicalStore(id);
        return ResponseEntity.noContent().build();
    }
}
