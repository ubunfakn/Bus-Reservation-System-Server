package com.ubunfakn.reservation.bus_reserv_systm.services.ServiceProvider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ubunfakn.reservation.bus_reserv_systm.model.Customer;
import com.ubunfakn.reservation.bus_reserv_systm.repository.CustomerRepository;
import com.ubunfakn.reservation.bus_reserv_systm.services.CustomerRepositoryService;

@Service
public class CustomerRepositoryServiceProvider implements CustomerRepositoryService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }
    
}
