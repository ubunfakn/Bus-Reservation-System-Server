package com.ubunfakn.reservation.bus_reserv_systm.services.ServiceProvider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ubunfakn.reservation.bus_reserv_systm.model.Routes;
import com.ubunfakn.reservation.bus_reserv_systm.repository.RouteRepository;
import com.ubunfakn.reservation.bus_reserv_systm.services.RoutesRepositoryService;

@Service
public class RoutesRepositoryServiceProvider implements RoutesRepositoryService {

    @Autowired
    private RouteRepository routesRepository;

    @Override
    public Routes saveRoutes(Routes routes) {
        return this.routesRepository.save(routes);
    }

    @Override
    public List<Routes> getAllRoutes(){
        return this.routesRepository.findAll();
    }

    @Override
    public void deleteRouteById(int id) {
        this.routesRepository.delete(this.routesRepository.findById(id));
    }

    @Override
    public Routes getRouteById(int id) {
        return this.routesRepository.findById(id);
    }

    @Override
    public List<Routes> getByOriginAndDestination(String origin, String destination) {
        return this.routesRepository.findByOriginAndDestination(origin, destination);
    }
    
}
