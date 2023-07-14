package com.ubunfakn.reservation.bus_reserv_systm.services;

import java.util.List;

import com.ubunfakn.reservation.bus_reserv_systm.model.Customer;

public interface CustomerRepositoryService {

    public List<Customer> getAllCustomers();
    public Customer getCustomerById(int id);
    public void deleteCustomerById(int id);
    public Customer saveCustomer(Customer customer);
}
