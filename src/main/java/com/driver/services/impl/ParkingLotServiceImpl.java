package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.driver.model.SpotType.*;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot newParkingLot = new ParkingLot();
        newParkingLot.setAddress(address);
        newParkingLot.setName(name);
        parkingLotRepository1.save(newParkingLot);
        return newParkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        Spot newSpot = new Spot();
        newSpot.setPricePerHour(pricePerHour);
        newSpot.setOccupied(false);
        if(numberOfWheels<=2) {
            newSpot.setSpotType(TWO_WHEELER);
        }
        else if(numberOfWheels<=4){
            newSpot.setSpotType(FOUR_WHEELER);
        }
        else{
            newSpot.setSpotType(OTHERS);
        }
        System.out.println(newSpot.getSpotType());
        System.out.println(numberOfWheels);
        ParkingLot pL = (parkingLotRepository1.findById(parkingLotId)).get();

        newSpot.setParkingLot(pL);

        pL.getSpotList().add(newSpot);
        parkingLotRepository1.save(pL);

        return newSpot;
    }

    @Override
    public void deleteSpot(int spotId) {
         Spot curSpot = spotRepository1.findById(spotId).get();

         ParkingLot curParkingLot = parkingLotRepository1.findById(curSpot.getParkingLot().getId()).get();

         List<Spot> allSpot = curParkingLot.getSpotList();

         allSpot.remove(curSpot);

         curParkingLot.setSpotList(allSpot);

         parkingLotRepository1.save(curParkingLot);
         spotRepository1.deleteById(spotId);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {

        Spot curSpot = spotRepository1.findById(spotId).get();

        ParkingLot curParkingLot = curSpot.getParkingLot();

        List<Spot> allSpot = curParkingLot.getSpotList();

        allSpot.remove(curSpot);

        curParkingLot.setSpotList(allSpot);
        parkingLotRepository1.save(curParkingLot);

        curSpot.setPricePerHour(pricePerHour);
        /////////////////////////////////////////////
        ParkingLot curParkingLot1 = parkingLotRepository1.findById(parkingLotId).get();
        curSpot.setParkingLot(curParkingLot1);
        List<Spot> allSpot1 = curParkingLot1.getSpotList();

        allSpot1.add(curSpot);

        curParkingLot1.setSpotList(allSpot1);

        parkingLotRepository1.save(curParkingLot1);

        return null;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
           parkingLotRepository1.deleteById(parkingLotId);
    }
}
