package com.ubunfakn.reservation.bus_reserv_systm.services.ServiceProvider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ubunfakn.reservation.bus_reserv_systm.model.Bookings;
import com.ubunfakn.reservation.bus_reserv_systm.model.User;
import com.ubunfakn.reservation.bus_reserv_systm.repository.BookingsRepository;
import com.ubunfakn.reservation.bus_reserv_systm.services.BookingsRepoService;

@Service
public class BookingsRepoServiceProvider implements BookingsRepoService {
    
    @Autowired
    private BookingsRepository bookingsRepository; // }

    @Override
    public Bookings saveBooking(Bookings bookings){
        return this.bookingsRepository.save(bookings);
    }

    @Override
    public List<Bookings> getAllBookings(){
        return this.bookingsRepository.findAll();
    }

    @Override
    public Bookings getBookingsByBookingId(long bookingId) {
        return this.bookingsRepository.findByBookingId(bookingId);
    }

    @Override
    public void deleteBooking(Bookings bookings){
        this.bookingsRepository.delete(bookings);
    }

    @Override
    public List<Bookings> getBookingsByUser(User user){
        return this.bookingsRepository.findByUser(user);
    }
    
}
