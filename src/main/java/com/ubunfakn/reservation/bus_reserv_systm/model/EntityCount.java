package com.ubunfakn.reservation.bus_reserv_systm.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntityCount {
    private int customerCount , busCount, routeCount, userCount, bookingCount ;
}
