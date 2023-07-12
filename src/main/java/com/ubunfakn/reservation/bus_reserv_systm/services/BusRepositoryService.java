package com.ubunfakn.reservation.bus_reserv_systm.services;


import java.util.List;

import com.ubunfakn.reservation.bus_reserv_systm.model.Bus;

public interface BusRepositoryService {

    public Bus saveBus(Bus bus);
    public List<Bus> getAllBuses();
    
}
