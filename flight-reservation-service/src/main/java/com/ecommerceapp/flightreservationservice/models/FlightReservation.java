package com.ecommerceapp.flightreservationservice.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import javax.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Document("flight_reservations")
public class FlightReservation {

    @Id
    private ObjectId _id;

    @NotNull(message="userìId parameter must not be null")
    private ObjectId userId;

    /*@NotNull(message="reservationDateTime parameter must not be null")
    private String reservationDateTime;*/

    @NotNull(message="ticketType parameter must not be null")
    private TicketType ticketType;

    @NotNull(message="passengersNumber parameter must not be null")
    private int passengersNumber;

    @NotNull(message="flightsReservationDetails parameter must not be null")
    private List<FlightReservationDetails> flightsReservationDetails = new ArrayList<FlightReservationDetails>();

    @NotNull(message="amount parameter must not be null")
    private BigDecimal amount;

    @NotNull(message="status parameter must not be null")
    private FlightReservationStatus status;

    @JsonCreator
    public FlightReservation(ObjectId userId, TicketType ticketType, int passengersNumber, List<FlightReservationDetails> flightsReservationDetails) {
        this._id = new ObjectId();
        this.userId = userId;
        this.ticketType = ticketType;
        this.passengersNumber = passengersNumber;
        this.flightsReservationDetails = flightsReservationDetails;
        this.status = FlightReservationStatus.PENDING;
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

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    @JsonGetter("userId")
    public String get_userId_string() {
        return userId.toHexString();
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public int getPassengersNumber() {
        return passengersNumber;
    }

    public void setPassengersNumber(int passengersNumber) {
        this.passengersNumber = passengersNumber;
    }

    public List<FlightReservationDetails> getFlightsReservationDetails() {
        return flightsReservationDetails;
    }

    public void setFlightsReservationDetails(List<FlightReservationDetails> flightsReservationDetails) {
        this.flightsReservationDetails = flightsReservationDetails;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public FlightReservationStatus getStatus() {
        return status;
    }

    public void setStatus(FlightReservationStatus status) {
        this.status = status;
    }

}