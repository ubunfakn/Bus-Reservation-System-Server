package com.ubunfakn.reservation.bus_reserv_systm.controller;

import java.util.*;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.ubunfakn.reservation.bus_reserv_systm.model.*;
import com.ubunfakn.reservation.bus_reserv_systm.services.*;
import com.ubunfakn.reservation.bus_reserv_systm.services.ServiceProvider.*;

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

    @Autowired
    private UserRepoService userRepoService;

    @Autowired
    private CustomerRepositoryService customerRepositoryService;

    @Autowired
    private PaymentServiceProvider paymentServiceProvider;

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
    public ResponseEntity<?> getAllBusesByRouteAndDate(@PathVariable("number") String number) {
        try {
            List<Passengers> passengers = this.passengersRepoService.getPassengersByBusNumber(number);
            Set<Integer> occupiedSeats = new HashSet<>();
            for (int i = 0; i < passengers.size(); i++) {
                occupiedSeats.add(passengers.get(i).getSeat());
            }
            if (occupiedSeats.size() == 0) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok().body(occupiedSeats);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/getprice/{number}")
    public ResponseEntity<?> getPriceOfTicket(@PathVariable("number") String number) {
        try {
            Routes route = this.routesRepositoryService.getByBusNumber(number);
            return ResponseEntity.ok(route);
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/savebooking")
    public ResponseEntity<?> saveBooking(@RequestBody BookingAndCustomer bookingAndCustomer) {
        Customer customer = bookingAndCustomer.getCustomer();
        Bookings bookings = bookingAndCustomer.getBookings();
        List<Passengers> passengers = bookingAndCustomer.getPassenger();
        Bus bus = this.busRepositoryService.getByNumber(bookings.getBusNumber());
        Routes routes = this.routesRepositoryService.getByBusNumber(bookings.getBusNumber());
        List<Integer> seats = bookingAndCustomer.getSeat();
        
        Customer customerFromDatabase = this.customerRepositoryService.getCustomerByEmail(customer.getEmail());
        if(customerFromDatabase == null){
            this.customerRepositoryService.saveCustomer(customer);
        }else{
            customer = customerFromDatabase;
        }
        bus.setAvailable(bus.getAvailable() - passengers.size());
        bookings.setBus(bus);
        bookings.setRoutes(routes);
        bookings.setBookingId(Math.round(Math.random() * (999999 - 100000)) + 1);
        bookings.setCustomer(customer);
        bookings.setUser(this.userRepoService.getUserByEmail(customer.getEmail()));

        this.bookingsRepoServiceProvider.saveBooking(bookings);
        for (int i = 0; i < passengers.size(); i++) {
            passengers.get(i).setBusNumber(bookings.getBus().getNumber());
            passengers.get(i).setBookings(bookings);
            passengers.get(i).setSeat(seats.get(i));
            this.passengersRepoService.savPassenger(passengers.get(i));
        }
        
        // Creating payment Order
        try {

            RazorpayClient razorpayClient = new RazorpayClient("rzp_test_5SKZYSprPJkNCr", "OB0ULkNjdwcn6kQlZQtLfkp2");

            // JSONOBJECT creation
            JSONObject jsonObject = new JSONObject();
            double amount = bookings.getTotalPrice();
            jsonObject.put("amount", amount * 100);
            jsonObject.put("currency", "INR");
            long num = Math.round(Math.random() * 99999);
            jsonObject.put("receipt", "receipt_" + num);

            // Creating order
            Order order = razorpayClient.orders.create(jsonObject);

            System.out.println(order);

            // Saving data to Database
            Payment payment = new Payment();
            payment.setAmount(bookings.getTotalPrice());
            payment.setOrderId(order.get("id"));
            payment.setCreated_at(order.get("created_at"));
            payment.setCurrency("INR");
            payment.setCustomer(customer);
            payment.setPartial_payment(false);
            payment.setPaymentId(null);
            payment.setReciept(order.get("receipt"));
            payment.setStatus("created");
            payment.setBookings(bookings);

            this.paymentServiceProvider.savPayment(payment);
            System.out.println(order.toString());
            return ResponseEntity.ok().body(order.toString());

        } catch (RazorpayException razorpayException) {
            throw new RuntimeException("Error in RazorPay Integration: " + razorpayException.getMessage());
        }
    }

    @PostMapping("/paymentupdate")
    public ResponseEntity<?> verifyAndSavePayment(@RequestBody Map<String, Object> data) {
        try {
            System.out.println(data + "*****************");
            String order_id = data.get("razorpay_order_id").toString();
            Payment payment = this.paymentServiceProvider.getByOrder_id(order_id);
            System.out.println(1);
            payment.setPaymentId(data.get("razorpay_payment_id").toString());
            System.out.println(5);
            payment.setStatus(data.get("paymentStatus").toString());
            System.out.println(2);
            payment = this.paymentServiceProvider.savPayment(payment);
            Bookings bookings = payment.getBookings();
            bookings.setStatus("Successful");
            this.bookingsRepoServiceProvider.saveBooking(bookings);
            return ResponseEntity.ok().body(bookings.getBookingId());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/view")
    public ResponseEntity<?> viewTicket(@RequestBody GetTicket getTicket) {

        try {
            Bookings bookings = this.bookingsRepoServiceProvider.getBookingsByBookingId(getTicket.getBookingid());
            if (bookings == null) {
                System.out.println("null");
                return ResponseEntity.notFound().build();
            } else {
                if (bookings.getCustomer().getMobile().equals(getTicket.getMobile())) {
                    Map <String,Object> data = new HashMap<>();
                    data.put("bookings", bookings);
                    data.put("customer", bookings.getCustomer());
                    data.put("passenger", bookings.getPassengers());
                    data.put("bus", bookings.getBus());
                    data.put("route", bookings.getRoutes());
                    return ResponseEntity.ok().body(data);
                }
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/cancel/{bookingId}")
    public ResponseEntity<?> cancelTicket(@PathVariable("bookingId")long bookingId) {
        try {
            Bookings bookings = this.bookingsRepoServiceProvider.getBookingsByBookingId(bookingId);
            System.out.println(bookings.getStatus());
            if(bookings.getStatus().equals("Successful")){
                System.out.println(bookingId);
                bookings.setStatus("Cancelled");
                this.bookingsRepoServiceProvider.saveBooking(bookings);
                Bus bus = bookings.getBus();
                List<Passengers> passengers = bookings.getPassengers();
                bus.setAvailable(bus.getAvailable()+passengers.size());
                this.busRepositoryService.saveBus(bus);
                for(int i=0;i<passengers.size();i++){
                    passengers.get(i).setSeat(0);
                    this.passengersRepoService.savPassenger(passengers.get(i));
                }
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
