package com.ubunfakn.reservation.bus_reserv_systm.services;

import java.util.List;

import com.ubunfakn.reservation.bus_reserv_systm.model.Bookings;

public interface BookingsRepoService {
    
    public Bookings saveBooking(Bookings bookings);
    public List<Bookings> getBookingsByMobile(String mobile);
    public List<Bookings> getAllBookings();
}
