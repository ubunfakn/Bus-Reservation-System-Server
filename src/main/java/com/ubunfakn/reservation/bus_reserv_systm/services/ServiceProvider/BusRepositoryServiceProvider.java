package com.ubunfakn.reservation.bus_reserv_systm.services.ServiceProvider;

import java.util.List;

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

    @Override
    public List<Bus> getAllBuses(){
        return this.busRepository.findAll();
    }

    @Override
    public void deleteBusById(int id) {
        this.busRepository.delete(this.busRepository.findById(id));
    }

    @Override
    public Bus getBusById(int id) {
        return this.busRepository.findById(id);
    }

    @Override
    public List<Bus> getByDepartureDate(String departureDate) {
        return this.busRepository.findByDepartureDate(departureDate);
    }

    @Override
    public Bus getByNumber(String number) {
        return this.busRepository.findByNumber(number);
    }
}
