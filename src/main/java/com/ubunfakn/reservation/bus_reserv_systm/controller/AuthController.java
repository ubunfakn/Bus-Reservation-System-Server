package com.ubunfakn.reservation.bus_reserv_systm.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ubunfakn.reservation.bus_reserv_systm.SecurityConfig.JWTHelper;
import com.ubunfakn.reservation.bus_reserv_systm.model.*;
import com.ubunfakn.reservation.bus_reserv_systm.model.User;
import com.ubunfakn.reservation.bus_reserv_systm.services.*;
import com.ubunfakn.reservation.bus_reserv_systm.services.ServiceProvider.EmailServiceProvider;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepoService userRepoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTHelper jwtHelper;

    @Autowired
    private ForgotPasswordRepoService forgotPasswordRepoServiceProvider;

    public void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                email, password);
        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (Exception e) {
            // TODO: handle exception
            throw new BadCredentialsException("Invalid Username or password");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest, Message message) {
        this.doAuthenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
        String token = this.jwtHelper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .token(token)
                .username(userDetails.getUsername())
                .role(userRepoService.getUserByEmail(userDetails.getUsername()).getRole()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<?> saveUser(@RequestBody User user, Message message) {
        System.out.println(user);
        user.setRole("ROLE_USER");
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user = this.userRepoService.saveUser(user);
        if (user != null) {
            try {
                UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(),
                        user.getPassword(), new ArrayList<>());
                String token = this.jwtHelper.generateToken(userDetails);
                JwtResponse jwtResponse = JwtResponse.builder().username(userDetails.getUsername())
                        .token(token)
                        .role(user.getRole())
                        .build();
                return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
            } catch (Exception e) {
                message.setMessage("Something went wrong from our end");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
            }
        } else {
            message.setMessage("Something went wrong from our end");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

    @PostMapping("/forgot")
    public ResponseEntity<?> verifyEmail(@RequestBody ForgotPassword forgotPassword1, Message message) {
        try {
            this.forgotPasswordRepoServiceProvider.truncateTable();
            String email = forgotPassword1.getEmail();
            System.out.println(email);
            User user = this.userRepoService.getUserByEmail(email);
            System.out.println(user);
            if (user == null) {
                System.out.println("email is not registered with us");
                message.setMessage("E-mail is not registered with us");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
            } else {
                int otp = (int) Math.floor(Math.random() * (10000 - 1000) + 1000);
                System.out.println(otp);
                ForgotPassword forgotPassword = new ForgotPassword();
                forgotPassword.setEmail(email);
                forgotPassword.setOtp(otp);
                this.forgotPasswordRepoServiceProvider.saveForgotPasswordData(forgotPassword);
                EmailServiceProvider.sendEmail(String.valueOf(otp), "Forgot Password verification OTP", email);
                message.setMessage("OTP sent to your registered email-id");
                return ResponseEntity.status(HttpStatus.OK).body(message);
            }
        } catch (Exception e) {
            message.setMessage("Something went wrong from our end");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(@RequestBody ForgotPassword forgotPassword1, Message message) {
        try {
            ForgotPassword forgotPassword = this.forgotPasswordRepoServiceProvider.getByOtp(forgotPassword1.getOtp());
            if (forgotPassword == null) {
                message.setMessage("Please enter correct OTP");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
            } else {
                message.setMessage("OTP verified");
                this.forgotPasswordRepoServiceProvider.truncateTable();
                return ResponseEntity.status(HttpStatus.OK).body(message);
            }
        } catch (Exception e) {
            message.setMessage("Something went wrong from our end");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

    @PostMapping("/change")
    public ResponseEntity<?> changePassword(@RequestBody JwtRequest jwtRequest, Message message) {
        try {
            // ForgotPassword forgotPassword = this.forgotPasswordRepoServiceProvider.getByEmail(jwtRequest.getEmail());
            // if (forgotPassword == null) {
            //     message.setMessage("Request denied!!");
            //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
            // }
            // this.forgotPasswordRepoServiceProvider.truncateTable();
            User user = this.userRepoService.getUserByEmail(jwtRequest.getEmail());
            user.setPassword(this.passwordEncoder.encode(jwtRequest.getPassword()));
            user = this.userRepoService.saveUser(user);
            return ResponseEntity.ok().body("Password Changed Successfully");
        } catch (Exception e) {
            message.setMessage("Something went wrong from our end");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> exceptionHandler() {
        return new ResponseEntity<>("Invalid Credentials!!", HttpStatus.BAD_REQUEST);
    }

}
