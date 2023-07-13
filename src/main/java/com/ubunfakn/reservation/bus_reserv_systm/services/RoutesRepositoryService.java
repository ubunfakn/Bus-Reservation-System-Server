package com.ubunfakn.reservation.bus_reserv_systm.services;

import java.util.List;


import com.ubunfakn.reservation.bus_reserv_systm.model.Routes;

public interface RoutesRepositoryService {
    
    public Routes saveRoutes(Routes routes);
    public Routes getRouteById(int id);
    public List<Routes> getAllRoutes();
    public void deleteRouteById(int id);
    public List<Routes> getByOriginAndDestination(String origin, String destination);
    public Routes getByBusNumber(String number);
}
