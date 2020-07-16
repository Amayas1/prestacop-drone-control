package com.prestacop.engine;

import com.google.gson.Gson;
import com.prestacop.config.SparkConfiguration;
import com.prestacop.models.Drone;
import com.prestacop.service.DroneSimulatorService;
import com.prestacop.service.DroneSimulatorServiceImpl;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.spark.api.java.function.ForeachPartitionFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;


@Service
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private static final String TOPIC_DRONE_STREAM_REGULAR = "droneStreamRegular";
    private static final String TOPIC_DRONE_STREAM_VIOLATION = "droneStreamViolation";
    private static final String TOPIC_DRONE_STREAM_INTERVENTION = "droneStreamIntervention";

    private static final String TOPIC_DRONE_HISTORY = "droneHistory";

    //private final String CSV_PATH = "/dev/datalake/drone/data/";
    //private final String CSV_PATH = "/home/cloudera/atldev01/drone-data";
    private static final String CSV_PATH = "/home/maria_dev/drone/data/Parking_Violations_Issued_-_Fiscal_Year_2015.csv";

    DroneSimulatorService droneSimulatorService = new DroneSimulatorServiceImpl();

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(Drone droneMessage) {
        logger.info(String.format("#### -> Producing message -> %s", droneMessage.toString()));

        String droneMessageJson = new Gson().toJson(droneMessage);

        if((droneMessage.getViolation_Code()==null || droneMessage.getViolation_Code().isEmpty())
        || (droneMessage.getPlate_ID()==null || droneMessage.getPlate_ID().isEmpty())){
            this.kafkaTemplate.send(TOPIC_DRONE_STREAM_REGULAR, droneMessageJson);
        } else if (droneMessage.getPlate_ID().equals(DroneSimulatorService.INTERVENTION_CODE)){
            this.kafkaTemplate.send(TOPIC_DRONE_STREAM_INTERVENTION, droneMessageJson);
        } else {
            this.kafkaTemplate.send(TOPIC_DRONE_STREAM_VIOLATION, droneMessageJson);
        }
    }

    public void sendSimulateMessages(Integer nbEvents){

        IntStream.range(1, nbEvents).forEach(o -> {
            sendMessage(droneSimulatorService.startDroneSimulation());
        });
    }

    public void sensHistoryViolationIssuesMessages(Integer nbEvents, Boolean allData){
        try{
            //Setting up a Stream to our CSV File.
            Stream<String> FileStream = Files.lines(Paths.get(CSV_PATH));
            //Here we are going to read each line using Foreach loop on the FileStream object
            FileStream.limit(nbEvents).forEach(line -> {
                //The topic the record will be appended to
                //The key that will be included in the record
                //The record contents
                final ProducerRecord<String, String> CsvRecord = new ProducerRecord<String, String>(
                        TOPIC_DRONE_HISTORY, UUID.randomUUID().toString(), line
                );

                System.out.println("producer{topic = "+TOPIC_DRONE_HISTORY+" id = "+UUID.randomUUID().toString()+" line = " +line+"}");
                this.kafkaTemplate.send(CsvRecord);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}