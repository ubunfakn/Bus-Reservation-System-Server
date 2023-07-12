package com.ubunfakn.reservation.bus_reserv_systm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ubunfakn.reservation.bus_reserv_systm.model.Bus;

public interface BusRepository extends JpaRepository<Bus, Integer> {
    
    public Bus findById(int id);
    public List<Bus> findByDepartureDate(String departureDate);
    public Bus findByNumber(String number);
}
