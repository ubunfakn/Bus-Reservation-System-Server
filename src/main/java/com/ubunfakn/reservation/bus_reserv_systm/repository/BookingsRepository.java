package com.ubunfakn.reservation.bus_reserv_systm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ubunfakn.reservation.bus_reserv_systm.model.Bookings;
import java.util.List;


public interface BookingsRepository extends JpaRepository<Bookings,Integer>{
    
    public List<Bookings> findByMobile(String mobile);
}
