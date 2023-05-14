package com.ecommerceapp.flightreservationservice.repositories;

import com.ecommerceapp.flightreservationservice.models.FlightFare;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface FlightFareRepository extends CrudRepository<FlightFare, ObjectId> {

}
