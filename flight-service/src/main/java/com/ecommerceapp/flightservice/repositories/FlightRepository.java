package com.ecommerceapp.flightservice.repositories;

import com.ecommerceapp.flightservice.models.Flight;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface FlightRepository extends CrudRepository<Flight, ObjectId> {

}
