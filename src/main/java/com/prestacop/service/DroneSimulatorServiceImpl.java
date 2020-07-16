package com.prestacop.service;

import com.prestacop.models.Drone;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Random;
import java.util.UUID;

@Service
public class DroneSimulatorServiceImpl implements DroneSimulatorService{

    @Override
    public Drone startDroneSimulation() {
        if (new Random().nextInt(5) == 1) return createDroneViolationMessage();
        return createDroneRegularMessage();
    }

    private Drone createDroneRegularMessage(){
        final Drone regularMessage =  new Drone(generateDroneId(),
                generateTimestamp(),
                generateRandomInRangeLatLong(),
                generateRandomInRangeLatLong(),
                null,
                null);
        return regularMessage;
    }

    private Drone createDroneViolationMessage(){
        final Drone violationMessage =  new Drone(generateDroneId(),
                generateTimestamp(),
                generateRandomInRangeLatLong(),
                generateRandomInRangeLatLong(),
                generatePlateId(),
                generateViolationCode());
        return violationMessage;
    }

    private String generateDroneId(){
        return UUID.randomUUID().toString();
    }

    private String generateTimestamp(){
        return new Timestamp(System.currentTimeMillis()).toString();
    }

    private String generateRandomInRangeLatLong() {
        final int from = -180;
        final int to = 180;
        final int fixed = 3;
        return BigDecimal.valueOf((Math.random() * (to - from) + from)).setScale(fixed, RoundingMode.HALF_EVEN).toString();
    }

    private String generatePlateId(){

        Random rnd = new Random();
        String plateId = new StringBuilder()
                .append(String.valueOf((char) ('A' + rnd.nextInt(26))))
                .append(String.valueOf((char) ('A' + rnd.nextInt(26))))
                .append(String.valueOf(rnd.nextInt(9)))
                .append(String.valueOf(rnd.nextInt(9)))
                .append(String.valueOf(rnd.nextInt(9)))
                .append(String.valueOf((char) ('A' + rnd.nextInt(26))))
                .append(String.valueOf((char) ('A' + rnd.nextInt(26))))
                .toString();

        // only 1% need intervention
        if (rnd.nextInt(100) == 1) {
            return INTERVENTION_CODE;
        }
        return plateId;
    }

    // In history data of Parking Violations Issued,
    // we can show that violation code interval is between 0 to 100
    private String generateViolationCode(){
        return String.valueOf((Integer)(new Random().nextInt(100)));
    }

}
