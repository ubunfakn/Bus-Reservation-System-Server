package com.ubunfakn.reservation.bus_reserv_systm.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTicket {
    
    private String mobile;
    private int bookingid;
}
