package com.ubunfakn.reservation.bus_reserv_systm.services;

import java.util.List;

import com.ubunfakn.reservation.bus_reserv_systm.model.Bookings;
import com.ubunfakn.reservation.bus_reserv_systm.model.User;

public interface BookingsRepoService {
    
    public Bookings saveBooking(Bookings bookings);
    public List<Bookings> getAllBookings();
    public Bookings getBookingsByBookingId(long bookingId);
    public void deleteBooking(Bookings bookings);
    public List<Bookings> getBookingsByUser(User user);
}
