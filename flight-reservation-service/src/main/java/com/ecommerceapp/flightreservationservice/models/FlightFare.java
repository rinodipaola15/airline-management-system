package com.ecommerceapp.flightreservationservice.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Document("flight_fares")
public class FlightFare {

    @Id
    private ObjectId _id;

    @NotNull(message="name parameter must not be null")
    private String name;

    @NotNull(message="description parameter must not be null")
    private String description;

    @NotNull(message="price parameter must not be null")
    private BigDecimal price;

    @JsonCreator
    public FlightFare(String name, String description, BigDecimal price) {
        this._id = new ObjectId();
        this.name = name;
        this.description = description;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}