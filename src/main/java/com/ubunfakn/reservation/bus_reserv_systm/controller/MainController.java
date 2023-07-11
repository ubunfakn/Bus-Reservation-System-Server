package com.ubunfakn.reservation.bus_reserv_systm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.ubunfakn.reservation.bus_reserv_systm.model.*;
import com.ubunfakn.reservation.bus_reserv_systm.services.ServiceProvider.BookingsRepoServiceProvider;

@RestController
@CrossOrigin("*")
public class MainController {

    @Autowired
    private BookingsRepoServiceProvider bookingsRepoServiceProvider;
    
    @PostMapping("/auth/api/cancel")
    public ResponseEntity<?> cancelTicket(@RequestBody GetTicket getTicket){
        
        try {
            System.out.println("cancel ticket request received");
            Bookings booking = null;
            List<Bookings> bookings = this.bookingsRepoServiceProvider.getBookingsByMobile(getTicket.getMobile());
            if(bookings.size()==0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No bookings found for this Mobile Number");
            }else{
                for(int i=0;i<bookings.size();i++)
                {
                    if(bookings.get(i).getId()==getTicket.getBookingid()){
                        booking = bookings.get(i);
                        break;
                    }
                }
                if(booking==null){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking Id not found for entered Mobile Number");
                }else{
                    //update booking status to cancelled
                    booking.setStatus("cancelled");
                    booking = this.bookingsRepoServiceProvider.saveBooking(booking);
                    return ResponseEntity.status(HttpStatus.OK).body(booking);
                }
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error!! Please try after sometime");
        }
    }


    @PostMapping("/view")
    public ResponseEntity<?> viewTicket(@RequestBody GetTicket getTicket){
        
        try {
            System.out.println("view ticket request received");
            Bookings booking = null;
            List<Bookings> bookings = this.bookingsRepoServiceProvider.getBookingsByMobile(getTicket.getMobile());
            if(bookings.size()==0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No bookings found for this Mobile Number");
            }else{
                for(int i=0;i<bookings.size();i++)
                {
                    if(bookings.get(i).getId()==getTicket.getBookingid()){
                        booking = bookings.get(i);
                        break;
                    }
                }
                if(booking==null)
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking Id not found for entered Mobile Number");
                else
                    return ResponseEntity.status(HttpStatus.OK).body(booking);
                
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error!! Please try after sometime");
        }
    }
}
