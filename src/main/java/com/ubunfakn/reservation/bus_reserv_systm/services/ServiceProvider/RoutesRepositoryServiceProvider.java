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
    
}
