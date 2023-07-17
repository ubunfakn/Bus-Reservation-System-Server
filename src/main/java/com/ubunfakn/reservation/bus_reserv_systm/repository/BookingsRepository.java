package com.ubunfakn.reservation.bus_reserv_systm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ubunfakn.reservation.bus_reserv_systm.model.Bookings;
import com.ubunfakn.reservation.bus_reserv_systm.model.User;

import java.util.List;



public interface BookingsRepository extends JpaRepository<Bookings,Integer>{
    
    public Bookings findByBookingId(long bookingId);
    public List<Bookings> findByUser(User user);
}
