package com.ubunfakn.reservation.bus_reserv_systm.model;

import java.util.*;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bookings {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String mobile;
    private String email;
    private String busNumber;
    private String boardingDate;
    private String arrivalDate;
    private String origin;
    private String destinantion;
    private String busType;
    private String status;
    @OneToMany(mappedBy = "bookings")
    private List<Passengers> passengers;
    @ManyToOne
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

}
