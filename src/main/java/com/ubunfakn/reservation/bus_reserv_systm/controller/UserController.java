package com.ubunfakn.reservation.bus_reserv_systm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.ubunfakn.reservation.bus_reserv_systm.model.*;
import com.ubunfakn.reservation.bus_reserv_systm.services.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth/api")
public class UserController {
    
    @Autowired
    private UserRepoService userRepoService;

    @Autowired
    private BookingsRepoService bookingsRepoService;

    @PostMapping("/getuser")
    public ResponseEntity<?> getUserByEmail(@RequestBody String email, Message message){
        try {
            User user = this.userRepoService.getUserByEmail(email);
            if(user!=null){
                return ResponseEntity.ok(user);
            }else{
                message.setMessage("Something went wrong from our end!! Please try again later");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
            }
        } catch (Exception e) {
            message.setMessage("Something went wrong from our end!! Please try again later");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

    @PutMapping("/editprofile")
    public ResponseEntity<?> editProfile(@RequestBody User user, Message message){
        try {
            User userFromDatabase = this.userRepoService.getUserByEmail(user.getEmail());
            user.setPassword(userFromDatabase.getPassword());
            user.setRole(userFromDatabase.getRole());
            this.userRepoService.saveUser(user);
            return ResponseEntity.status(200).body(user);

        } catch (Exception e) {
            System.out.println(e);
            message.setMessage("Something went wrong from our end!! Please try again later");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

    @PostMapping("/user/bookings")
    public ResponseEntity<?> getAllBookings(@RequestBody String email){
        System.out.println(email);
        try {
                List<Bookings> bookings = this.bookingsRepoService.getBookingsByUser(this.userRepoService.getUserByEmail(email));
                List<BookingResponse> bookingResponses = new ArrayList<>();
                if(bookings.size()==0){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }else{
                    for(int i=0;i<bookings.size();i++)
                    {
                        BookingResponse bookingResponse = new BookingResponse();
                        bookingResponse.setBoardingDate(bookings.get(i).getBus().getDepartureDate());
                        bookingResponse.setBookingId(bookings.get(i).getBookingId());
                        bookingResponse.setBusNumber(bookings.get(i).getBusNumber());
                        bookingResponse.setBusType(bookings.get(i).getBus().getType());
                        bookingResponse.setCid(bookings.get(i).getCustomer().getId());
                        bookingResponse.setDestination(bookings.get(i).getRoutes().getDestination());
                        bookingResponse.setEmail(bookings.get(i).getCustomer().getEmail());
                        bookingResponse.setMobile(bookings.get(i).getCustomer().getMobile());
                        bookingResponse.setOrigin(bookings.get(i).getRoutes().getOrigin());
                        bookingResponse.setPassengerCount(bookings.get(i).getPassengers().size());
                        bookingResponse.setStatus(bookings.get(i).getStatus());
                        bookingResponses.add(bookingResponse);
                    }
                return ResponseEntity.ok().body(bookingResponses);
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
