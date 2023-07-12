package com.ubunfakn.reservation.bus_reserv_systm.services;

import java.util.List;

import com.ubunfakn.reservation.bus_reserv_systm.model.Routes;

public interface RoutesRepositoryService {
    
    public Routes saveRoutes(Routes routes);
    public List<Routes> getAllRoutes();
}
