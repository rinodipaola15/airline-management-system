package com.ecommerceapp.flightservice.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotNull;

@Document("airports")
public class Airport {

    @Id
    private ObjectId _id;

    @NotNull(message="name parameter must not be null")
    private String name;

    @NotNull(message="city parameter must not be null")
    private String city;

    @JsonCreator
    public Airport(String name, String city) {
        this._id = new ObjectId();
        this.name = name;
        this.city = city;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}