package com.ubunfakn.reservation.bus_reserv_systm.services;

import com.ubunfakn.reservation.bus_reserv_systm.model.User;

public interface UserRepoService {
    
    public User getUserByEmail(String email);
    public User saveUser(User user);
}
