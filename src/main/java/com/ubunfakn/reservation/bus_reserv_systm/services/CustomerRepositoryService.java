package com.ubunfakn.reservation.bus_reserv_systm.services;

import java.util.List;

import com.ubunfakn.reservation.bus_reserv_systm.model.Customer;

public interface CustomerRepositoryService {

    public List<Customer> getAllCustomers();
    
}