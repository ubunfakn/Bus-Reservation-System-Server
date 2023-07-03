package com.ubunfakn.reservation.bus_reserv_systm.services;

import com.ubunfakn.reservation.bus_reserv_systm.model.ForgotPassword;

public interface ForgotPasswordRepoService {
    
    public ForgotPassword saveForgotPasswordData(ForgotPassword forgotPassword);
    public ForgotPassword getByEmail(String email);
    public ForgotPassword getByOtp(int otp);
    public void truncateTable();
}
