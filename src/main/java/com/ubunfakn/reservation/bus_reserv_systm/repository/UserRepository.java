package com.ubunfakn.reservation.bus_reserv_systm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ubunfakn.reservation.bus_reserv_systm.model.User;


public interface UserRepository extends JpaRepository<User,Integer> {
    
    public User findByEmail(String email);

    public User findById(int id);
}
