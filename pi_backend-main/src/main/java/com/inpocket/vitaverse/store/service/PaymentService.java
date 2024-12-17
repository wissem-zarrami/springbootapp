package com.inpocket.vitaverse.store.service;

import com.inpocket.vitaverse.store.entity.Location;
import com.inpocket.vitaverse.store.entity.Payment;
import com.inpocket.vitaverse.store.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentService {
    PaymentRepository paymentRepository;

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }
    public void setShipLocation(Long userId, String location) {
        Optional<Payment> payment = paymentRepository.findByShipmentUserUserUserId(userId);
        if(payment.isPresent())
        {
            var value = payment.get();
            value.setShipmentLocation(location);
            paymentRepository.save(value);
        }
    }

    public float getShipDistance(Long id, String userLocation) throws RuntimeException
    {
        Optional<Payment> payment = paymentRepository.findById(id);
        if(payment.isPresent())
        {
            var value = payment.get();
            var shipperLocation = value.getShipmentLocation();
            if(shipperLocation != null)
            {
                var paymentLoc = Location.parseFromString(value.getShipmentLocation());
                var userLoc = Location.parseFromString(userLocation);
                var dist = Location.calcDistance(paymentLoc, userLoc);
                return dist;
            }
            else
            {
                System.out.println("payment has no shipper!");
                //throw new RuntimeException("shipment has no shipper!");
            }
        }
        throw new RuntimeException("payment with id:"+id+" not found.");
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    public Optional<Payment> getPaymentByUserId(Long userId) {
        return paymentRepository.findByShoppingCartUserUserId(userId);
    }

    public Optional<Payment> getPaymentByShipmentUser(Long shipmentUserId) {
        return paymentRepository.findByShipmentUserUserUserId(shipmentUserId);
    }
}
