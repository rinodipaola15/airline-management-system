package com.ecommerceapp.flightreservationservice.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import javax.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Document("users")
public class User {

    @Id
    private ObjectId _id;

    @NotNull(message="firstName parameter must not be null")
    private String firstName;

    @NotNull(message="lastName parameter must not be null")
    private String lastName;

    @NotNull(message="email parameter must not be null")
    @Indexed(unique=true)
    private String email;

    @NotNull(message="birthDate parameter must not be null")
    private String birthDate;

    @NotNull(message="country parameter must not be null")
    private String country;

    @NotNull(message="phoneNumber parameter must not be null")
    private String phoneNumber;

    @JsonCreator
    public User(ObjectId _id, String firstName, String lastName, String email, String birthDate, String country, String phoneNumber) {
        this._id = new ObjectId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.country = country;
        this.phoneNumber = phoneNumber;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
