package com.inpocket.vitaverse.store.service;


import com.inpocket.vitaverse.store.entity.PhysicalStore;
import com.inpocket.vitaverse.store.repository.PhysicalStoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PhysicalStoreService {
    private final PhysicalStoreRepository physicalStoreRepository;

    // Create
    public PhysicalStore createPhysicalStore(PhysicalStore physicalStore) {
        return physicalStoreRepository.save(physicalStore);
    }

    // Read
    public List<PhysicalStore> getAllPhysicalStores() {
        return physicalStoreRepository.findAll();
    }

    public Optional<PhysicalStore> getPhysicalStoreById(Long id) {
        return physicalStoreRepository.findById(id);
    }

    // Update
    public PhysicalStore updatePhysicalStore(Long id, PhysicalStore updatedPhysicalStore) {
        Optional<PhysicalStore> existingPhysicalStoreOptional = physicalStoreRepository.findById(id);
        if (existingPhysicalStoreOptional.isPresent()) {
            PhysicalStore existingPhysicalStore = existingPhysicalStoreOptional.get();

            existingPhysicalStore.setDescription(updatedPhysicalStore.getDescription());
            existingPhysicalStore.setName(updatedPhysicalStore.getName());
            existingPhysicalStore.setImage(updatedPhysicalStore.getImage());
            existingPhysicalStore.setLocation(updatedPhysicalStore.getLocation());
            existingPhysicalStore.setWorkHours(updatedPhysicalStore.getWorkHours());

            return physicalStoreRepository.save(existingPhysicalStore);
        }
        return null; // or throw exception indicating physical store not found
    }

    // Delete
    public void deletePhysicalStore(Long id) {
        physicalStoreRepository.deleteById(id);
    }
}
