package com.ubunfakn.reservation.bus_reserv_systm.services.ServiceProvider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ubunfakn.reservation.bus_reserv_systm.model.User;
import com.ubunfakn.reservation.bus_reserv_systm.repository.UserRepository;
import com.ubunfakn.reservation.bus_reserv_systm.services.UserRepoService;

@Service
public class UserRepoServiceProvider implements UserRepoService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public User saveUser(User user){
         return this.userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    @Override
    public User getUserByUserId(int id) {
        return this.userRepository.findById(id);
    }
}
