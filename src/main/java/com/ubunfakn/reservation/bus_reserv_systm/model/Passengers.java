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
    private int id;
    private String passengerName;
    private int seat;
    private String busNumber;
    private int age;
    private String gender;
    @ManyToOne(fetch = FetchType.LAZY)
    private Bookings bookings;
}
