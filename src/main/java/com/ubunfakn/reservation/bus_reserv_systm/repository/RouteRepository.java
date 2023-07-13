package com.ubunfakn.reservation.bus_reserv_systm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ubunfakn.reservation.bus_reserv_systm.model.Routes;


public interface RouteRepository extends JpaRepository<Routes, Integer>{
    
    public Routes findById(int id);
    public List<Routes> findByOriginAndDestination(String origin, String destination);
    public Routes findByNumber(String number);
}
