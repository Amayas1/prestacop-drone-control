package com.prestacop.service;

import com.prestacop.models.Drone;
import org.springframework.context.annotation.Bean;

public interface DroneSimulatorService {
     String INTERVENTION_CODE = "NEED_INTERVENTION";

     Drone startDroneSimulation();
}
