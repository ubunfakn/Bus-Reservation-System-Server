package com.ubunfakn.reservation.bus_reserv_systm.services.ServiceProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ubunfakn.reservation.bus_reserv_systm.model.Bus;
import com.ubunfakn.reservation.bus_reserv_systm.repository.BusRepository;
import com.ubunfakn.reservation.bus_reserv_systm.services.BusRepositoryService;

@Service
public class BusRepositoryServiceProvider implements BusRepositoryService{

    @Autowired
    private BusRepository busRepository;

    @Override
    public Bus saveBus(Bus bus) {
        return this.busRepository.save(bus);
    }
}
