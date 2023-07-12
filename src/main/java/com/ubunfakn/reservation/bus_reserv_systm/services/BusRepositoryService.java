package com.ubunfakn.reservation.bus_reserv_systm.services;


import java.util.List;

import com.ubunfakn.reservation.bus_reserv_systm.model.Bus;

public interface BusRepositoryService {

    public Bus saveBus(Bus bus);
    public Bus getBusById(int id);
    public List<Bus> getAllBuses();
    public void deleteBusById(int id);
    public List<Bus> getByDepartureDate(String departureDate);
    public Bus getByNumber(String number);
    
}
