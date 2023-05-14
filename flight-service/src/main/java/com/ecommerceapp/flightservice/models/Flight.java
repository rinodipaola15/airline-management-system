package com.ecommerceapp.flightservice.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import javax.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@NoArgsConstructor
@Document("flights")
public class Flight {

    @Id
    private ObjectId _id;

    @NotNull(message="maxPassengersNumber parameter must not be null")
    private int maxPassengersNumber;

    @NotNull(message="departureCity parameter must not be null")
    private String departureCity;

    @NotNull(message="destinationCity parameter must not be null")
    private String destinationCity;

    @NotNull(message="departureDateTime parameter must not be null")
    private String departureDateTime;

    @NotNull(message="arrivalDateTime parameter must not be null")
    private String arrivalDateTime;

    @NotNull(message="departureAirport parameter must not be null")
    private ObjectId departureAirportId;

    @NotNull(message="destinationAirport parameter must not be null")
    private ObjectId destinationAirportId;

    @NotNull(message="price parameter must not be null")
    private BigDecimal price;

    @JsonCreator
    public Flight(int maxPassengersNumber, String departureCity, ObjectId departureAirportId, String departureDateTime, String destinationCity, ObjectId destinationAirportId, String arrivalDateTime, BigDecimal price) {
        this._id = new ObjectId();
        this.maxPassengersNumber = maxPassengersNumber;
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.departureAirportId = departureAirportId;
        this.destinationAirportId = destinationAirportId;
        this.price = price;
    }

    public ObjectId get_id() {
        return _id;
    }

    @JsonGetter("_id")
    public String get_id_string() {
        return _id.toHexString();
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public int getMaxPassengersNumber() {
        return maxPassengersNumber;
    }

    public void setMaxPassengersNumber(int maxPassengersNumber) {
        this.maxPassengersNumber = maxPassengersNumber;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(String departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public String getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(String arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public ObjectId getDepartureAirportId() {
        return departureAirportId;
    }

    public void setDepartureAirportId(ObjectId departureAirportId) {
        this.departureAirportId = departureAirportId;
    }

    public ObjectId getDestinationAirportId() {
        return destinationAirportId;
    }

    public void setDestinationAirportId(ObjectId destinationAirportId) {
        this.destinationAirportId = destinationAirportId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
