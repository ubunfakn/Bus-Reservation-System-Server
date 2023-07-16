package com.ubunfakn.reservation.bus_reserv_systm.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String password;
    @Column(unique = true)
    private String mobile;
    private String address;
    private String state;
    private String country;
    private String role;

    @OneToMany(mappedBy="user")
    private List<Bookings> bookings;
}
