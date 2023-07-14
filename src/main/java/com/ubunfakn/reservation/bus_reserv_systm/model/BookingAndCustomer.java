package com.ubunfakn.reservation.bus_reserv_systm.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingAndCustomer {
    
    private Bookings bookings;
    private Customer customer;
    private List<Passengers> passenger;
    private List<Integer> seat;
}
