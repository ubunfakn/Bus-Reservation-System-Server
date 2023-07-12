package com.ubunfakn.reservation.bus_reserv_systm.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequiredBus {
    
    private String origin;
    private String destination;
    private String date;
}
