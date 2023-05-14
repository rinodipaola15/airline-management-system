package com.ecommerceapp.flightservice.repositories;

import com.ecommerceapp.flightservice.models.Airport;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface AirportRepository extends CrudRepository<Airport, ObjectId> {

}
