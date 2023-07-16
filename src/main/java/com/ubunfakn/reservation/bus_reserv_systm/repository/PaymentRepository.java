package com.ubunfakn.reservation.bus_reserv_systm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ubunfakn.reservation.bus_reserv_systm.model.Payment;


public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    
    public Payment findByOrderId(String orderId);
}
