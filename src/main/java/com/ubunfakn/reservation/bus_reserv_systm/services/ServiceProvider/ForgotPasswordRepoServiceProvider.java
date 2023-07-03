package com.ubunfakn.reservation.bus_reserv_systm.services.ServiceProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ubunfakn.reservation.bus_reserv_systm.model.ForgotPassword;
import com.ubunfakn.reservation.bus_reserv_systm.repository.ForgotPasswordRepo;
import com.ubunfakn.reservation.bus_reserv_systm.services.ForgotPasswordRepoService;

@Service
public class ForgotPasswordRepoServiceProvider implements ForgotPasswordRepoService {
    
    @Autowired
    private ForgotPasswordRepo forgotPasswordRepo;

    @Override
    public ForgotPassword saveForgotPasswordData(ForgotPassword forgotPassword) {
        return this.forgotPasswordRepo.save(forgotPassword);
    }

    @Override
    public ForgotPassword getByEmail(String email) {
        return this.forgotPasswordRepo.findByEmail(email);
    }

    @Override
    public ForgotPassword getByOtp(int otp){
        return this.forgotPasswordRepo.findByOtp(otp);
    }

    @Override
    public void truncateTable() {
        this.forgotPasswordRepo.deleteAll();
    }

}
