package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.driver.model.PaymentMode.*;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
        Reservation reservation = reservationRepository2.findById(reservationId).get();

        int totalcost = reservation.getSpot().getPricePerHour()*reservation.getNumberOfHours();
        if(amountSent<totalcost){
            throw new Exception("Insufficient Amount");
        }
        if(PaymentMode.valueOf(mode).equals(CASH) || PaymentMode.valueOf(mode).equals(UPI) || PaymentMode.valueOf(mode).equals(CARD)) {
            Payment p = new Payment();
            p.setPaymentCompleted(true);
            p.setPaymentMode(PaymentMode.valueOf(mode));
            p.setReservation(reservation);
            reservationRepository2.save(reservation);
            Payment f = paymentRepository2.save(p);
            return f;
        }
        else{
            throw new Exception("Payment mode not detected");
        }
    }
}
