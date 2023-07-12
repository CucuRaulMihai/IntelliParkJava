package com.example.intellipark;

import javafx.collections.ObservableList;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Date;
import java.util.Observable;

public class AdminSearch {
    Integer clientID;
    String name, email, license;
    String vehicle_type;

    public AdminSearch(Integer clientID, String name, String email, String license, String vehicle_type) {
        this.clientID = clientID;
        this.name = name;
        this.email = email;
        this.license = license;
        this.vehicle_type = vehicle_type;
    }

    public Integer getClientID() {
        return clientID;
    }

    public void setClientID(Integer clientID) {
        this.clientID = clientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

}
