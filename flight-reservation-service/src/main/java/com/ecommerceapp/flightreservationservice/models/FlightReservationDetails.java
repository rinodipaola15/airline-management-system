package com.ecommerceapp.flightreservationservice.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class FlightReservationDetails {

    @NotNull(message="flightId parameter must not be null")
    private ObjectId flightId;

    @NotNull(message="passengersData parameter must not be null")
    private List<PassengerData> passengersData = new ArrayList<PassengerData>();

    public FlightReservationDetails(ObjectId flightId, List<PassengerData> passengersData) {
        this.flightId = flightId;
        this.passengersData = passengersData;
    }

    public ObjectId getFlightId() {
        return flightId;
    }

    @JsonGetter("flightId")
    public String get_flightId_string() {
        return flightId.toHexString();
    }

    public void setFlightId(ObjectId flightId) {
        this.flightId = flightId;
    }

    public List<PassengerData> getPassengersData() {
        return passengersData;
    }

    public void setPassengersData(List<PassengerData> passengersData) {
        this.passengersData = passengersData;
    }

}
