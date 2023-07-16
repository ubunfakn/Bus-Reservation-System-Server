package com.ubunfakn.reservation.bus_reserv_systm.services.ServiceProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ubunfakn.reservation.bus_reserv_systm.model.Payment;
import com.ubunfakn.reservation.bus_reserv_systm.repository.PaymentRepository;
import com.ubunfakn.reservation.bus_reserv_systm.services.PaymentRepoService;

@Service
public class PaymentServiceProvider implements PaymentRepoService {
    
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment savPayment(Payment payment) {
        return this.paymentRepository.save(payment);
    }

    @Override
    public Payment getByOrder_id(String order_id) {
        return this.paymentRepository.findByOrderId(order_id);
    }


}
