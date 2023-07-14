package com.ubunfakn.reservation.bus_reserv_systm.services;

import java.util.List;

import com.ubunfakn.reservation.bus_reserv_systm.model.Passengers;

public interface PassengersRepoService {
    
    public List<Passengers> getPassengersByBusNumber(String busNumber);
    public Passengers savPassenger(Passengers passengers);
}
