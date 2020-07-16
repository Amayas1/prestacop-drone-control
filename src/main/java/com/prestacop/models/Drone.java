package com.prestacop.models;

public class Drone {

    private String Drone_Id;
    private String Drone_Time;
    private String Drone_Location_latitude;
    private String Drone_Location_longitude;
    private String Plate_ID;
    private String Violation_Code;

    public Drone(String drone_Id, String drone_Time, String drone_Location_latitude, String drone_Location_longitude, String plate_ID, String violation_Code) {
        Drone_Id = drone_Id;
        Drone_Time = drone_Time;
        Drone_Location_latitude = drone_Location_latitude;
        Drone_Location_longitude = drone_Location_longitude;
        Plate_ID = plate_ID;
        Violation_Code = violation_Code;
    }

    public String getDrone_Id() {
        return Drone_Id;
    }

    public void setDrone_Id(String drone_Id) {
        Drone_Id = drone_Id;
    }

    public String getDrone_Time() {
        return Drone_Time;
    }

    public void setDrone_Time(String drone_Time) {
        Drone_Time = drone_Time;
    }

    public String getDrone_Location_latitude() {
        return Drone_Location_latitude;
    }

    public void setDrone_Location_latitude(String drone_Location_latitude) {
        Drone_Location_latitude = drone_Location_latitude;
    }

    public String getDrone_Location_longitude() {
        return Drone_Location_longitude;
    }

    public void setDrone_Location_longitude(String drone_Location_longitude) {
        Drone_Location_longitude = drone_Location_longitude;
    }

    public String getPlate_ID() {
        return Plate_ID;
    }

    public void setPlate_ID(String plate_ID) {
        Plate_ID = plate_ID;
    }

    public String getViolation_Code() {
        return Violation_Code;
    }

    public void setViolation_Code(String violation_Code) {
        Violation_Code = violation_Code;
    }

    @Override
    public String toString() {
        return "Drone{" +
                "Drone_Id='" + Drone_Id + '\'' +
                ", Drone_Time='" + Drone_Time + '\'' +
                ", Drone_Location_latitude='" + Drone_Location_latitude + '\'' +
                ", Drone_Location_longitude='" + Drone_Location_longitude + '\'' +
                ", Plate_ID='" + Plate_ID + '\'' +
                ", Violation_Code='" + Violation_Code + '\'' +
                '}';
    }
}