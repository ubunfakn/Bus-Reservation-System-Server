package com.ubunfakn.reservation.bus_reserv_systm.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Routes {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String number;
    private String pickUpPoint;
    private String dropOffPoint;
    private String pickUpTime;
    private String dropOffTime;
}
