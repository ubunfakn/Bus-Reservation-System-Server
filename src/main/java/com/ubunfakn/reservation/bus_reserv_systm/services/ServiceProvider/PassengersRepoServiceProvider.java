package com.ubunfakn.reservation.bus_reserv_systm.services.ServiceProvider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ubunfakn.reservation.bus_reserv_systm.model.Passengers;
import com.ubunfakn.reservation.bus_reserv_systm.repository.PassengersRepository;
import com.ubunfakn.reservation.bus_reserv_systm.services.PassengersRepoService;

@Service
public class PassengersRepoServiceProvider implements PassengersRepoService {

    @Autowired
    private PassengersRepository passengersRepository;
    
    @Override
    public List<Passengers> getPassengersByBusNumber(String busNumber){
        return this.passengersRepository.findByBusNumber(busNumber);
    }

    @Override
    public Passengers savPassenger(Passengers passengers){
        return this.passengersRepository.save(passengers);
    }
}
