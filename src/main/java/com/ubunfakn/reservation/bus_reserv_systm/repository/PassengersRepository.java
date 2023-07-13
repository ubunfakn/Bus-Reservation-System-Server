package com.ubunfakn.reservation.bus_reserv_systm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ubunfakn.reservation.bus_reserv_systm.model.Passengers;

public interface PassengersRepository extends JpaRepository<Passengers, Integer> {
    
    public List<Passengers> findByBusNumber(String number);
}
