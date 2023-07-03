package com.ubunfakn.reservation.bus_reserv_systm.services.ServiceProvider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ubunfakn.reservation.bus_reserv_systm.model.Bookings;
import com.ubunfakn.reservation.bus_reserv_systm.repository.BookingsRepository;
import com.ubunfakn.reservation.bus_reserv_systm.services.BookingsRepoService;

@Service
public class BookingsRepoServiceProvider implements BookingsRepoService {
    
    @Autowired
    private BookingsRepository bookingsRepository;

    @Override
    public List<Bookings> getBookingsByMobile(String mobile) {
        return this.bookingsRepository.findByMobile(mobile);
    }

    @Override
    public Bookings saveBooking(Bookings bookings){
        return this.saveBooking(bookings);
    }
    
}
