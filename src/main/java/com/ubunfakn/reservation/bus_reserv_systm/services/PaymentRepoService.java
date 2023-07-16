package com.ubunfakn.reservation.bus_reserv_systm.services;

import com.ubunfakn.reservation.bus_reserv_systm.model.Payment;

public interface PaymentRepoService {
    
    public Payment savPayment(Payment payment);
    public Payment getByOrder_id(String order_id);
}
