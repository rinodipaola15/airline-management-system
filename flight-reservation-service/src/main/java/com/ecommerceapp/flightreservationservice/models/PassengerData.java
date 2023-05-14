package com.ecommerceapp.flightreservationservice.models;

import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

public class PassengerData {

    @NotNull(message="name parameter must not be null")
    private String name;

    @NotNull(message="surname parameter must not be null")
    private String surname;

    @NotNull(message="birthDate parameter must not be null")
    private String birthDate;

    @NotNull(message="flightFare parameter must not be null")
    private ObjectId flightFare;

    @NotNull(message="extraLuggage parameter must not be null")
    private Map<ObjectId, Integer> extraLuggage = new HashMap<ObjectId, Integer>();

    @NotNull(message="travelInsurance parameter must not be null")
    private boolean travelInsurance;

    @NotNull(message="fastTrack parameter must not be null")
    private boolean fastTrack;

    public PassengerData(String name, String surname, String birthDate, ObjectId flightFare, Map<ObjectId, Integer> extraLuggage, boolean travelInsurance, boolean fastTrack) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.flightFare = flightFare;
        this.extraLuggage = extraLuggage;
        this.travelInsurance = travelInsurance;
        this.fastTrack = fastTrack;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public ObjectId getFlightFare() {
        return flightFare;
    }

    public void setFlightFare(ObjectId flightFare) {
        this.flightFare = flightFare;
    }

    public Map<ObjectId, Integer> getExtraLuggage() {
        return extraLuggage;
    }

    public void setExtraLuggage(Map<ObjectId, Integer> extraLuggage) {
        this.extraLuggage = extraLuggage;
    }

    public boolean isTravelInsurance() {
        return travelInsurance;
    }

    public void setTravelInsurance(boolean travelInsurance) {
        this.travelInsurance = travelInsurance;
    }

    public boolean isFastTrack() {
        return fastTrack;
    }

    public void setFastTrack(boolean fastTrack) {
        this.fastTrack = fastTrack;
    }

}
