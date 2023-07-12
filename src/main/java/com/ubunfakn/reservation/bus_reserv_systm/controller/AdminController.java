package com.ubunfakn.reservation.bus_reserv_systm.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<List<Bookings>> getAllBookings(){
        try {
            List<Bookings> bookings = this.bookingsRepoService.getAllBookings();
            if(bookings.size()==0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }else{
                return ResponseEntity.ok(bookings);
            }
        } catch (Exception e) {
            throw e;
        }
    }

}
