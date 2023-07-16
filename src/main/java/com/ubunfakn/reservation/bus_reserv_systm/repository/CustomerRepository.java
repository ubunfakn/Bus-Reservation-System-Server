package com.ubunfakn.reservation.bus_reserv_systm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ubunfakn.reservation.bus_reserv_systm.model.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    
    public Customer findById(int id);
    public Customer findByEmail(String email);
}
