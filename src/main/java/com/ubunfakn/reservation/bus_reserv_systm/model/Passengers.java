package com.ubunfakn.reservation.bus_reserv_systm.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Passengers {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int seat;
    private String busNumber;
    private int age;
    private String gender;
    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
    private Bookings bookings;
}
