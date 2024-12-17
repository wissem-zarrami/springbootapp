package com.inpocket.vitaverse.store.service;

import com.inpocket.vitaverse.store.entity.ShipmentUser;
import com.inpocket.vitaverse.store.repository.ShipmentUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShipmentUserService {
    private final ShipmentUserRepository shipmentUserRepository;
    public ShipmentUser createShipmentUser(ShipmentUser shipmentUser) {
        return shipmentUserRepository.save(shipmentUser);
    }

    public List<ShipmentUser> getAllShipmentUsers() {
        return shipmentUserRepository.findAll();
    }

    public Optional<ShipmentUser> getShipmentUserById(Long id) {
        return shipmentUserRepository.findById(id);
    }

    public ShipmentUser updateShipmentUser(Long id, ShipmentUser updatedShipmentUser) {
        Optional<ShipmentUser> optionalShipmentUser = shipmentUserRepository.findById(id);
        if (optionalShipmentUser.isPresent()) {
            ShipmentUser existingShipmentUser = optionalShipmentUser.get();
            existingShipmentUser.setAvailable(updatedShipmentUser.isAvailable());
            existingShipmentUser.setUser(updatedShipmentUser.getUser());
            return shipmentUserRepository.save(existingShipmentUser);
        }
        return null;
    }

    public void deleteShipmentUser(Long id) {
        shipmentUserRepository.deleteById(id);
    }

}
