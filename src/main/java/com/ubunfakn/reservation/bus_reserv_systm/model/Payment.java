package com.ubunfakn.reservation.bus_reserv_systm.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String orderId;
    private Date created_at;
    private String paymentId;
    private int amount;
    private String currency;
    private String reciept;
    private String []notes;
    private boolean partial_payment;
    private String status;
    @OneToOne(cascade = CascadeType.ALL)
    private Bookings bookings;
    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;
}
