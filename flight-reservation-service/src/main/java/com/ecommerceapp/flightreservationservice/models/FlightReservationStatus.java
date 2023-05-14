package com.ecommerceapp.flightreservationservice.models;

import java.io.Serializable;

public enum FlightReservationStatus implements Serializable {
    PENDING,
    CONFIRMED,
    REJECTED
}