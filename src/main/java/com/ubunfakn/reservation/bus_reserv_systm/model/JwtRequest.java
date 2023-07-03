package com.ubunfakn.reservation.bus_reserv_systm.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {
    
    private String email;
    private String password;
}
