package com.ecommerceapp.flightreservationservice.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Document("luggage")
public class Luggage {

    @Id
    private ObjectId _id;

    @NotNull(message="maxWeight parameter must not be null")
    private double maxWeight; //Kg

    @NotNull(message="maxSize parameter must not be null")
    private String maxSize; //heightxwidthxthickness

    @NotNull(message="description parameter must not be null")
    private String description;

    @NotNull(message="price parameter must not be null")
    private BigDecimal price;

    @JsonCreator
    public Luggage(double maxWeight, String maxSize, String description, BigDecimal price) {
        this._id = new ObjectId();
        this.maxWeight = maxWeight;
        this.maxSize = maxSize;
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

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public String getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(String maxSize) {
        this.maxSize = maxSize;
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
