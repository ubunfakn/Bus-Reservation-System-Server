package com.ubunfakn.reservation.bus_reserv_systm.model;
import java.util.*;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String mobile;
    private String email;
    @OneToMany(mappedBy="customer")
    private List<Bookings> bookings;
}
