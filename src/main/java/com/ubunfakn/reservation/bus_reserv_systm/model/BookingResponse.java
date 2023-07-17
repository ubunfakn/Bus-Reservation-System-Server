package com.ubunfakn.reservation.bus_reserv_systm.model;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    
    private long bookingId;
    private String origin;
    private String destination;
    private String busNumber;
    private String boardingDate;
    private String busType;
    private int passengerCount;
    private int cid;
    private String email;
    private String mobile;
    private String status;
}
