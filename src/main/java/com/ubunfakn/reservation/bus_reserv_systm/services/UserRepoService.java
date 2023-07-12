package com.ubunfakn.reservation.bus_reserv_systm.services;

import java.util.List;

import com.ubunfakn.reservation.bus_reserv_systm.model.User;

public interface UserRepoService {
    
    public User getUserByEmail(String email);
    public User getUserByUserId(int id);
    public User saveUser(User user);
    public List<User> getAllUsers();
}
