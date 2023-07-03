package com.ubunfakn.reservation.bus_reserv_systm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ubunfakn.reservation.bus_reserv_systm.model.ForgotPassword;



public interface ForgotPasswordRepo extends JpaRepository<ForgotPassword,Integer> {
    
    public ForgotPassword findByEmail(String email);

    public ForgotPassword findByOtp(int otp);
}
