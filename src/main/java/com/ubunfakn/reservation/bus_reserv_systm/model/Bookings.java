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
    private Date boardingDate;
    private String origin;
    private String destinantion;
    @Column(unique = true)
    private int bookingid;
    private String busType;
    private String status;
    @OneToMany(mappedBy = "bookings")
    private List<Passengers> passengers;
    @ManyToOne
    private User user;

}
