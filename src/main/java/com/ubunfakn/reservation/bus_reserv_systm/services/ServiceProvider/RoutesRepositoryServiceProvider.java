package com.ubunfakn.reservation.bus_reserv_systm.services.ServiceProvider;

import org.springframework.stereotype.Service;

import com.ubunfakn.reservation.bus_reserv_systm.model.Routes;
import com.ubunfakn.reservation.bus_reserv_systm.repository.RouteRepository;
import com.ubunfakn.reservation.bus_reserv_systm.services.RoutesRepositoryService;

@Service
public class RoutesRepositoryServiceProvider implements RoutesRepositoryService {

    private RouteRepository routesRepository;

    @Override
    public Routes saveRoutes(Routes routes) {
        return this.routesRepository.save(routes);
    }
    
}
