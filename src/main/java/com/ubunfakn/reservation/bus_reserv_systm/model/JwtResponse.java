package com.ubunfakn.reservation.bus_reserv_systm.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    
    private String token;
    private String username;
    private String role;
}
