package com.ubunfakn.reservation.bus_reserv_systm.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

import com.ubunfakn.reservation.bus_reserv_systm.model.*;
import com.ubunfakn.reservation.bus_reserv_systm.services.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth/admin/api")
public class AdminController {

    @Autowired
    private BusRepositoryService busRepositoryService;

    @Autowired
    private RoutesRepositoryService routesRepositoryService;

    @Autowired
    private UserRepoService userRepoService;

    @Autowired
    private CustomerRepositoryService customerRepositoryService;

    @Autowired
    private BookingsRepoService bookingsRepoService;

    @PostMapping("/addbus")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addBus(@RequestBody Bus bus, Message message) {
        bus.setAvailable(bus.getCapacity());
        try {
            this.busRepositoryService.saveBus(bus);
            message.setMessage("Bus saved");
            return ResponseEntity.status(200).body(message);
        } catch (Exception e) {
            message.setMessage("Something went wrong from our end");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

    @PostMapping("/addroute")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addRoute(@RequestBody Routes route, Message message) {
        System.out.println(route);
        try {
            this.routesRepositoryService.saveRoutes(route);
            message.setMessage("Route saved");
            return ResponseEntity.status(200).body(message);
        } catch (Exception e) {
            message.setMessage("Something went wrong from our end");
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

    @GetMapping("/buses")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Bus>> getAllBuses(){
        try {
            List<Bus> buses = this.busRepositoryService.getAllBuses();
            if(buses.size()==0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }else{
                return ResponseEntity.ok(buses);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/routes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Routes>> getAllRoutes(){
        try {
            List<Routes> routes = this.routesRepositoryService.getAllRoutes();
            if(routes.size()==0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }else{
                return ResponseEntity.ok(routes);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers(){
        try {
            List<User> users = this.userRepoService.getAllUsers();
            if(users.size()==0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }else{
                for(int i=0;i<users.size();i++){
                    users.get(i).setPassword(null);
                }
                return ResponseEntity.ok(users);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/customers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        try {
            List<Customer> customers = this.customerRepositoryService.getAllCustomers();
            if(customers.size()==0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }else{
                return ResponseEntity.ok(customers);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/bookings")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllBookings(){
        try {
                List<Bookings> bookings = this.bookingsRepoService.getAllBookings();
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

    @DeleteMapping("/deleteroute/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteRoute(@PathVariable("id")int id){
        try {
            this.routesRepositoryService.deleteRouteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @DeleteMapping("/deletebus/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBus(@PathVariable("id")int id){
        try {
            this.busRepositoryService.deleteBusById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @DeleteMapping("/deletecustomer/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id")int id){
        try {
            this.customerRepositoryService.deleteCustomerById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/countall")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityCount> countAll(EntityCount entityCount){
        try {
            List<Customer> customers = this.customerRepositoryService.getAllCustomers();
            List<Bus> buses = this.busRepositoryService.getAllBuses();
            List<Routes> routes = this.routesRepositoryService.getAllRoutes();
            List<User> users = this.userRepoService.getAllUsers();
            List<Bookings> bookings = this.bookingsRepoService.getAllBookings();
            entityCount.setBookingCount(bookings.size());
            entityCount.setBusCount(buses.size());
            entityCount.setCustomerCount(customers.size());
            entityCount.setRouteCount(routes.size());
            entityCount.setUserCount(users.size());
            return ResponseEntity.ok().body(entityCount);
        } catch (Exception e) {
            throw e;
        }
    }


}
