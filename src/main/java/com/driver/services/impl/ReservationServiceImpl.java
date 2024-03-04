package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.Integer.MAX_VALUE;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        Optional<User> optionaluser = userRepository3.findById(userId);
        if(!optionaluser.isPresent()){
            throw new Exception("Cannot make reservation");
        }
       User curuser = optionaluser.get();
        Optional<ParkingLot> OptionalParkinglot = parkingLotRepository3.findById(parkingLotId);
        if(!OptionalParkinglot.isPresent()){
            throw new Exception("Cannot make reservation");
        }
       ParkingLot curparkinglot = OptionalParkinglot.get();

       List<Spot> spots = curparkinglot.getSpotList();
       Spot goodspots = null;
       Integer price=MAX_VALUE;
       if(numberOfWheels>4) {
           for (Spot s : spots) {
               if(s.getSpotType().equals(SpotType.OTHERS)){
                   if(s.getPricePerHour()<price && s.getOccupied().equals(false)){
                       goodspots = s;
                       price = s.getPricePerHour();
                   }
               }
           }
       }
       else if(numberOfWheels>2){
           for (Spot s : spots) {
               if(s.getSpotType().equals(SpotType.OTHERS) || s.getSpotType().equals(SpotType.FOUR_WHEELER)){
                   if(s.getPricePerHour()<price && s.getOccupied().equals(false)){
                       goodspots = s;
                       price = s.getPricePerHour();
                   }
               }
           }
       }
       else{
           for (Spot s : spots) {
                   if(s.getPricePerHour()<price && s.getOccupied().equals(false)) {
                       goodspots = s;
                       price = s.getPricePerHour();
                   }
           }
       }

       if(goodspots==null){
           throw new Exception("Cannot make reservation");
       }

       Reservation reservation = new Reservation();
       reservation.setNumberOfHours(timeInHours);
       reservation.setSpot(goodspots);
       reservation.setUser(curuser);
       reservation.getSpot().setOccupied(true);
       reservationRepository3.save(reservation);
       return reservation;
    }
}
