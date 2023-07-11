package com.ubunfakn.reservation.bus_reserv_systm.controller;

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

    @PostMapping("/addbus")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addBus(@RequestBody Bus bus, Message message) {
        bus.setAvailable(bus.getCapacity());
        try {
            this.busRepositoryService.saveBus(bus);
            message.setMessage("Bus saved");
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message.setMessage("Something went wrong from our end");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

    @PostMapping("/addroute")
    public ResponseEntity<?> addRoute(@RequestBody Routes route, Message message) {
        try {
            this.routesRepositoryService.saveRoutes(route);
            message.setMessage("Route saved");
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message.setMessage("Something went wrong from our end");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }
}
