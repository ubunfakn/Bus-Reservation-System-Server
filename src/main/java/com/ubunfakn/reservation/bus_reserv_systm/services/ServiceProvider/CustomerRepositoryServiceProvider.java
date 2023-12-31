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

    @Override
    public Customer getCustomerById(int id) {
        return this.customerRepository.findById(id);
    }

    @Override
    public void deleteCustomerById(int id) {
        this.customerRepository.delete(this.customerRepository.findById(id));
    }

    @Override
    public Customer saveCustomer(Customer customer){
        return this.customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerByEmail(String email){
        return this.customerRepository.findByEmail(email);
    }

    
    
}
