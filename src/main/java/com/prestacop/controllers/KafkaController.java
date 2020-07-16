package com.prestacop.controllers;

import com.prestacop.engine.Producer;
import com.prestacop.models.Drone;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

    private final Producer producer;

    @Autowired
    KafkaController(Producer producer) {
        this.producer = producer;
    }

    @ApiOperation(value = "Permet au drone d'envoyer un message a notre solution", response = String.class)
    @PutMapping(value = "/publish")
    @ResponseBody
    public Drone sendMessageToKafkaTopic(@RequestBody Drone drone) {

        System.out.println("information about my drone " + drone.toString());
        this.producer.sendMessage(drone);

        return drone;
    }

    @ApiOperation(value = "Permet de générer N events pour simuler un drone en activité", response = String.class)
    @PutMapping(value = "/publishEvents")
    @ResponseBody
    public String sendMessageWithSimulateDrone(@RequestParam(value = "nbEvents", defaultValue = "10") Integer nbEvents) {
        this.producer.sendSimulateMessages(nbEvents);
        return "The simulator generated " + nbEvents + " events" ;
    }

    @ApiOperation(value = "Permet de charger N events en se basant sur l'historique des données du drone", response = String.class)
    @PutMapping(value = "/publishEventsHistory")
    @ResponseBody
    public String sendDroneHistoricalData(@RequestParam(value = "nbEvents", defaultValue = "10", required = false) Integer nbEvents, @RequestParam(value = "allData", defaultValue = "false") Boolean allData) {
        this.producer.sensHistoryViolationIssuesMessages(nbEvents, allData);
        if(!allData){
            return "Sending " + nbEvents +" of the historical parking violation issues data";
        }else {
            return "Sending all the historical parking violation issues data";
        }
    }
}