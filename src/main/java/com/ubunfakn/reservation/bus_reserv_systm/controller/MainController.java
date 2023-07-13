package com.ubunfakn.reservation.bus_reserv_systm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.ubunfakn.reservation.bus_reserv_systm.model.*;
import com.ubunfakn.reservation.bus_reserv_systm.services.BusRepositoryService;
import com.ubunfakn.reservation.bus_reserv_systm.services.PassengersRepoService;
import com.ubunfakn.reservation.bus_reserv_systm.services.RoutesRepositoryService;
import com.ubunfakn.reservation.bus_reserv_systm.services.ServiceProvider.BookingsRepoServiceProvider;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth/api")
public class MainController {

    @Autowired
    private BookingsRepoServiceProvider bookingsRepoServiceProvider;

    @Autowired
    private RoutesRepositoryService routesRepositoryService;

    @Autowired
    private BusRepositoryService busRepositoryService;

    @Autowired
    private PassengersRepoService passengersRepoService;

    @PostMapping("/getBuses")
    public ResponseEntity<List<Bus>> getBusesByRouteAndDate(@RequestBody RequiredBus requiredBus, Message message) {
        try {
            System.out.println(requiredBus.getDestination());
            System.out.println(requiredBus.getOrigin());
            List<Routes> listOfRoutes = this.routesRepositoryService.getByOriginAndDestination(requiredBus.getOrigin(),
                    requiredBus.getDestination());
            List<Bus> listOfBus = new ArrayList<>();
            if (listOfRoutes.size() == 0) {
                return ResponseEntity.notFound().build();
            } else {
                for (int i = 0; i < listOfRoutes.size(); i++) {
                    Bus bus = this.busRepositoryService.getByNumber(listOfRoutes.get(i).getNumber());
                    if (requiredBus.getDate().equals(bus.getDepartureDate())) {
                        listOfBus.add(bus);
                    }
                    
                }
            }
            if (listOfBus.size() == 0) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(listOfBus);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/getbus/{number}")
    public ResponseEntity<?> getAllBusesByRouteAndDate(@PathVariable("number") String number){
        try {
            List<Passengers> passengers = this.passengersRepoService.getPassengersByBusNumber(number);
            List<Integer> occupiedSeats = new ArrayList<>();
            for(int i=0;i<passengers.size();i++){
                occupiedSeats.add(passengers.get(i).getSeat());
            }
            occupiedSeats.add(5);
            occupiedSeats.add(8);
            occupiedSeats.add(3);
            if(occupiedSeats.size()==0){
                return ResponseEntity.notFound().build();
            }else{
                return ResponseEntity.ok().body(occupiedSeats);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/getprice/{number}")
    public ResponseEntity<?> getPriceOfTicket(@PathVariable("number") String number){
        try {
            Routes route = this.routesRepositoryService.getByBusNumber(number);
            return ResponseEntity.ok(route);
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancelTicket(@RequestBody GetTicket getTicket) {

        try {
            System.out.println("cancel ticket request received");
            Bookings booking = null;
            List<Bookings> bookings = this.bookingsRepoServiceProvider.getBookingsByMobile(getTicket.getMobile());
            if (bookings.size() == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No bookings found for this Mobile Number");
            } else {
                for (int i = 0; i < bookings.size(); i++) {
                    if (bookings.get(i).getId() == getTicket.getBookingid()) {
                        booking = bookings.get(i);
                        break;
                    }
                }
                if (booking == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Booking Id not found for entered Mobile Number");
                } else {
                    // update booking status to cancelled
                    booking.setStatus("cancelled");
                    booking = this.bookingsRepoServiceProvider.saveBooking(booking);
                    return ResponseEntity.status(HttpStatus.OK).body(booking);
                }
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error!! Please try after sometime");
        }
    }

    @PostMapping("/view")
    public ResponseEntity<?> viewTicket(@RequestBody GetTicket getTicket) {

        try {
            System.out.println("view ticket request received");
            Bookings booking = null;
            List<Bookings> bookings = this.bookingsRepoServiceProvider.getBookingsByMobile(getTicket.getMobile());
            if (bookings.size() == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No bookings found for this Mobile Number");
            } else {
                for (int i = 0; i < bookings.size(); i++) {
                    if (bookings.get(i).getId() == getTicket.getBookingid()) {
                        booking = bookings.get(i);
                        break;
                    }
                }
                if (booking == null)
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Booking Id not found for entered Mobile Number");
                else
                    return ResponseEntity.status(HttpStatus.OK).body(booking);

            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error!! Please try after sometime");
        }
    }
}
