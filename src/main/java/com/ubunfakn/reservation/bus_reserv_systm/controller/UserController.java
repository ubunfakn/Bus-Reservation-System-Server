package com.ubunfakn.reservation.bus_reserv_systm.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.ubunfakn.reservation.bus_reserv_systm.model.*;
import com.ubunfakn.reservation.bus_reserv_systm.services.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth/api")
public class UserController {
    
    @Autowired
    private UserRepoService userRepoService;

    @PostMapping("/getuser")
    public ResponseEntity<?> getUserByEmail(@RequestBody String email, Message message){
        try {
            User user = this.userRepoService.getUserByEmail(email);
            if(user!=null){
                return ResponseEntity.ok(user);
            }else{
                message.setMessage("Something went wrong from our end!! Please try again later");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
            }
        } catch (Exception e) {
            message.setMessage("Something went wrong from our end!! Please try again later");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

    @PutMapping("/editprofile")
    public ResponseEntity<?> editProfile(@RequestBody User user, Message message){
        try {
            User userFromDatabase = this.userRepoService.getUserByEmail(user.getEmail());
            user.setPassword(userFromDatabase.getPassword());
            user.setRole(userFromDatabase.getRole());
            this.userRepoService.saveUser(user);
            return ResponseEntity.status(200).body(user);

        } catch (Exception e) {
            System.out.println(e);
            message.setMessage("Something went wrong from our end!! Please try again later");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }
}
